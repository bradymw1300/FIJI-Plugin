package edu.purdue.rochetlab.labquant.core;

import edu.purdue.rochetlab.labquant.config.JsonProvenanceWriter;
import edu.purdue.rochetlab.labquant.config.OutputDirectoryPlanner;
import edu.purdue.rochetlab.labquant.images.ImageContext;
import edu.purdue.rochetlab.labquant.io.ImageLoaderService;
import edu.purdue.rochetlab.labquant.io.RecursiveImageFinder;
import edu.purdue.rochetlab.labquant.logging.LabQuantLogger;
import edu.purdue.rochetlab.labquant.logging.ProgressReporter;
import edu.purdue.rochetlab.labquant.qc.QcExporter;
import edu.purdue.rochetlab.labquant.results.AssayResult;
import edu.purdue.rochetlab.labquant.results.CsvResultsWriter;
import edu.purdue.rochetlab.labquant.results.FailedImageReportWriter;
import edu.purdue.rochetlab.labquant.results.ResultsAggregator;
import ij.ImagePlus;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.List;

public class WorkflowRunner {
    private final RecursiveImageFinder imageFinder;
    private final ImageLoaderService imageLoader;
    private final ModuleRegistry moduleRegistry;
    private final OutputDirectoryPlanner outputPlanner;
    private final QcExporter qcExporter;
    private final ProgressReporter progressReporter;
    private final LabQuantLogger logger;

    public WorkflowRunner(RecursiveImageFinder imageFinder, ImageLoaderService imageLoader,
            ModuleRegistry moduleRegistry, OutputDirectoryPlanner outputPlanner, QcExporter qcExporter,
            ProgressReporter progressReporter, LabQuantLogger logger) {
        this.imageFinder = imageFinder;
        this.imageLoader = imageLoader;
        this.moduleRegistry = moduleRegistry;
        this.outputPlanner = outputPlanner;
        this.qcExporter = qcExporter;
        this.progressReporter = progressReporter;
        this.logger = logger;
    }

    public WorkflowSummary run(WorkflowConfig config, AssayConfig assayConfig, CancellationToken cancellationToken)
            throws AssayException {
        OutputDirectoryPlanner.OutputLayout layout = outputPlanner.prepare(config.getOutputFolder());
        ResultsAggregator aggregator = new ResultsAggregator();
        AssayModule assay = moduleRegistry.require(config.getAssayId());
        List<Path> images;
        try {
            images = imageFinder.find(config.getInputFolder().toPath(), config.getFileExtensions(),
                    config.isRecursive());
        } catch (IOException e) {
            throw new AssayException("Could not discover input images", e);
        }
        BatchJob batchJob = new BatchJob(images);
        int completed = 0;
        int failed = 0;
        boolean cancelled = false;

        try {
            writeWorkflowConfig(config, layout);
            int index = 0;
            for (ImageJob job : batchJob.getImageJobs()) {
                if (cancellationToken.isCancellationRequested() || Thread.currentThread().isInterrupted()) {
                    job.setStatus(ImageJobStatus.CANCELLED);
                    cancelled = true;
                    break;
                }
                index++;
                progressReporter.report("Processing " + job.getSourcePath().getFileName(), index - 1, images.size());
                job.setStatus(ImageJobStatus.RUNNING);
                ImagePlus imagePlus = null;
                try {
                    imagePlus = imageLoader.open(job.getSourcePath());
                    ImageContext imageContext = new ImageContext(job.getSourcePath(), imagePlus);
                    AssayResult result = assay.analyze(imageContext, assayConfig);
                    aggregator.add(result);
                    if (config.isExportQc()) {
                        qcExporter.export(imageContext, layout.getQcDir());
                    }
                    job.setStatus(ImageJobStatus.COMPLETED);
                    completed++;
                } catch (Exception e) {
                    job.setStatus(ImageJobStatus.FAILED);
                    failed++;
                    String stacktraceName = writeStacktrace(layout.getStacktracesDir(), job.getSourcePath(), e);
                    aggregator.addFailure(new ImageFailure(job.getSourcePath(), e.getClass().getName(),
                            safeMessage(e), stacktraceName));
                    logger.warn("Failed image: " + job.getSourcePath() + " (" + safeMessage(e) + ")");
                } finally {
                    if (imagePlus != null) {
                        imagePlus.close();
                    }
                    progressReporter.report("Processed " + index + " of " + images.size(), index, images.size());
                }
            }
        } finally {
            WorkflowSummary summary = new WorkflowSummary(images.size(), completed, failed, cancelled);
            writeResults(aggregator, layout);
            writeRunManifest(summary, layout);
            progressReporter.done("LabQuant batch complete");
        }

        return new WorkflowSummary(images.size(), completed, failed, cancelled);
    }

    private static String writeStacktrace(File stacktracesDir, Path sourcePath, Exception e) {
        String baseName = sourcePath.getFileName() == null ? "image" : sourcePath.getFileName().toString();
        String safeBaseName = baseName.replaceAll("[^A-Za-z0-9._-]", "_");
        File target = new File(stacktracesDir, safeBaseName + ".stacktrace.txt");
        int suffix = 1;
        while (target.exists()) {
            target = new File(stacktracesDir, safeBaseName + "." + suffix + ".stacktrace.txt");
            suffix++;
        }
        try {
            PrintWriter writer = new PrintWriter(target, "UTF-8");
            try {
                writer.println("Source image: " + sourcePath.toString());
                writer.println();
                StringWriter buffer = new StringWriter();
                e.printStackTrace(new PrintWriter(buffer));
                writer.print(buffer.toString());
            } finally {
                writer.close();
            }
            return target.getName();
        } catch (Exception writeFailure) {
            return "";
        }
    }

    private static String safeMessage(Throwable throwable) {
        return throwable.getMessage() == null ? "" : throwable.getMessage();
    }

    private void writeWorkflowConfig(WorkflowConfig config, OutputDirectoryPlanner.OutputLayout layout) {
        try {
            new JsonProvenanceWriter().writeWorkflowConfig(config, layout.getWorkflowConfigFile());
        } catch (IOException e) {
            logger.warn("Could not write workflow_config.json: " + safeMessage(e));
        }
    }

    private void writeResults(ResultsAggregator aggregator, OutputDirectoryPlanner.OutputLayout layout) {
        try {
            new CsvResultsWriter().write(aggregator.getRecords(), layout.getPerImageResultsFile());
        } catch (IOException e) {
            logger.warn("Could not write per_image_results.csv: " + safeMessage(e));
        }
        try {
            new FailedImageReportWriter().write(aggregator.getFailures(), layout.getFailedImagesFile());
        } catch (IOException e) {
            logger.warn("Could not write failed_images.csv: " + safeMessage(e));
        }
    }

    private void writeRunManifest(WorkflowSummary summary, OutputDirectoryPlanner.OutputLayout layout) {
        try {
            new JsonProvenanceWriter().writeRunManifest(summary, layout.getRunManifestFile());
        } catch (IOException e) {
            logger.warn("Could not write run_manifest.json: " + safeMessage(e));
        }
    }
}

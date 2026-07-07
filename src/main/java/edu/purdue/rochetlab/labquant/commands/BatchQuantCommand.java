package edu.purdue.rochetlab.labquant.commands;

import edu.purdue.rochetlab.labquant.assays.dummy.DummyAssayModule;
import edu.purdue.rochetlab.labquant.config.OutputDirectoryPlanner;
import edu.purdue.rochetlab.labquant.core.AssayConfig;
import edu.purdue.rochetlab.labquant.core.AssayException;
import edu.purdue.rochetlab.labquant.core.CancellationToken;
import edu.purdue.rochetlab.labquant.core.ModuleRegistry;
import edu.purdue.rochetlab.labquant.core.WorkflowConfig;
import edu.purdue.rochetlab.labquant.core.WorkflowRunner;
import edu.purdue.rochetlab.labquant.core.WorkflowSummary;
import edu.purdue.rochetlab.labquant.io.BasicImageLoaderService;
import edu.purdue.rochetlab.labquant.io.RecursiveImageFinder;
import edu.purdue.rochetlab.labquant.logging.FijiStatusProgressReporter;
import edu.purdue.rochetlab.labquant.logging.LabQuantLogger;
import edu.purdue.rochetlab.labquant.qc.NoOpQcExporter;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Command.class, menuPath = "Plugins>LabQuant>Run Batch Quantification")
public class BatchQuantCommand implements Command {

    @Parameter(label = "Input folder", style = "directory")
    private File inputFolder;

    @Parameter(label = "Output folder", style = "directory")
    private File outputFolder;

    @Parameter(label = "Recursive")
    private boolean recursive = true;

    @Parameter(label = "File extensions")
    private String fileExtensions = "tif,tiff,png,jpg,jpeg";

    @Parameter(label = "Export QC")
    private boolean exportQc = false;

    @Override
    public void run() {
        try {
            WorkflowConfig config = new WorkflowConfig(inputFolder, outputFolder, recursive,
                    parseExtensions(fileExtensions), exportQc, "dummy");
            ModuleRegistry registry = new ModuleRegistry();
            registry.register(new DummyAssayModule());

            WorkflowRunner runner = new WorkflowRunner(
                    new RecursiveImageFinder(),
                    new BasicImageLoaderService(),
                    registry,
                    new OutputDirectoryPlanner(),
                    new NoOpQcExporter(),
                    new FijiStatusProgressReporter(),
                    new LabQuantLogger());

            WorkflowSummary summary = runner.run(config, AssayConfig.empty(), CancellationToken.none());
            FijiStatusProgressReporter.showFinished(summary);
        } catch (AssayException e) {
            LabQuantLogger.defaultLogger().error("LabQuant batch failed", e);
        }
    }

    private static List<String> parseExtensions(String extensions) {
        if (extensions == null || extensions.trim().isEmpty()) {
            return Arrays.asList("tif", "tiff", "png", "jpg", "jpeg");
        }
        return Arrays.asList(extensions.split("\\s*,\\s*"));
    }
}

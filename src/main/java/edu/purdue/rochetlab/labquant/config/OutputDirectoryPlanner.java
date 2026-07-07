package edu.purdue.rochetlab.labquant.config;

import java.io.File;

public class OutputDirectoryPlanner {
    public OutputLayout prepare(File selectedOutputFolder) {
        File root = new File(selectedOutputFolder, "LabQuant_Output");
        OutputLayout layout = new OutputLayout(root);
        layout.mkdirs();
        return layout;
    }

    public static class OutputLayout {
        private final File rootDir;
        private final File resultsDir;
        private final File qcDir;
        private final File logsDir;
        private final File stacktracesDir;
        private final File provenanceDir;
        private final File tempDir;

        public OutputLayout(File rootDir) {
            this.rootDir = rootDir;
            this.resultsDir = new File(rootDir, "results");
            this.qcDir = new File(rootDir, "qc");
            this.logsDir = new File(rootDir, "logs");
            this.stacktracesDir = new File(logsDir, "stacktraces");
            this.provenanceDir = new File(rootDir, "provenance");
            this.tempDir = new File(rootDir, "temp");
        }

        public void mkdirs() {
            resultsDir.mkdirs();
            qcDir.mkdirs();
            stacktracesDir.mkdirs();
            provenanceDir.mkdirs();
            tempDir.mkdirs();
        }

        public File getRootDir() {
            return rootDir;
        }

        public File getQcDir() {
            return qcDir;
        }

        public File getStacktracesDir() {
            return stacktracesDir;
        }

        public File getPerImageResultsFile() {
            return new File(resultsDir, "per_image_results.csv");
        }

        public File getFailedImagesFile() {
            return new File(resultsDir, "failed_images.csv");
        }

        public File getWorkflowConfigFile() {
            return new File(provenanceDir, "workflow_config.json");
        }

        public File getRunManifestFile() {
            return new File(provenanceDir, "run_manifest.json");
        }
    }
}

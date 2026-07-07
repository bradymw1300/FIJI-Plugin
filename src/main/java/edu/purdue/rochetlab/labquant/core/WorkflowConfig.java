package edu.purdue.rochetlab.labquant.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkflowConfig {
    private final File inputFolder;
    private final File outputFolder;
    private final boolean recursive;
    private final List<String> fileExtensions;
    private final boolean exportQc;
    private final String assayId;

    public WorkflowConfig(File inputFolder, File outputFolder, boolean recursive,
            List<String> fileExtensions, boolean exportQc, String assayId) {
        this.inputFolder = inputFolder;
        this.outputFolder = outputFolder;
        this.recursive = recursive;
        this.fileExtensions = new ArrayList<String>(fileExtensions);
        this.exportQc = exportQc;
        this.assayId = assayId;
    }

    public File getInputFolder() {
        return inputFolder;
    }

    public File getOutputFolder() {
        return outputFolder;
    }

    public boolean isRecursive() {
        return recursive;
    }

    public List<String> getFileExtensions() {
        return Collections.unmodifiableList(fileExtensions);
    }

    public boolean isExportQc() {
        return exportQc;
    }

    public String getAssayId() {
        return assayId;
    }
}

package edu.purdue.rochetlab.labquant.core;

import java.nio.file.Path;

public class ImageJob {
    private final Path sourcePath;
    private ImageJobStatus status = ImageJobStatus.PENDING;

    public ImageJob(Path sourcePath) {
        this.sourcePath = sourcePath;
    }

    public Path getSourcePath() {
        return sourcePath;
    }

    public ImageJobStatus getStatus() {
        return status;
    }

    public void setStatus(ImageJobStatus status) {
        this.status = status;
    }
}

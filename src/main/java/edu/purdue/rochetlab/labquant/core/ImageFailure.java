package edu.purdue.rochetlab.labquant.core;

import java.nio.file.Path;

public class ImageFailure {
    private final Path sourcePath;
    private final String errorType;
    private final String message;
    private final String stacktraceFile;

    public ImageFailure(Path sourcePath, String errorType, String message, String stacktraceFile) {
        this.sourcePath = sourcePath;
        this.errorType = errorType;
        this.message = message;
        this.stacktraceFile = stacktraceFile;
    }

    public Path getSourcePath() {
        return sourcePath;
    }

    public String getErrorType() {
        return errorType;
    }

    public String getMessage() {
        return message;
    }

    public String getStacktraceFile() {
        return stacktraceFile;
    }
}

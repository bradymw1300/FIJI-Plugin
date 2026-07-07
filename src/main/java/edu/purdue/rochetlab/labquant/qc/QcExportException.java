package edu.purdue.rochetlab.labquant.qc;

public class QcExportException extends Exception {
    public QcExportException(String message) {
        super(message);
    }

    public QcExportException(String message, Throwable cause) {
        super(message, cause);
    }
}

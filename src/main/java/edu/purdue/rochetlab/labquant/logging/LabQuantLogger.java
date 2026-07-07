package edu.purdue.rochetlab.labquant.logging;

import ij.IJ;

public class LabQuantLogger {
    public static LabQuantLogger defaultLogger() {
        return new LabQuantLogger();
    }

    public void info(String message) {
        IJ.log("[LabQuant] " + message);
    }

    public void warn(String message) {
        IJ.log("[LabQuant WARN] " + message);
    }

    public void error(String message, Throwable throwable) {
        IJ.log("[LabQuant ERROR] " + message);
        if (throwable != null) {
            IJ.handleException(throwable);
        }
    }
}

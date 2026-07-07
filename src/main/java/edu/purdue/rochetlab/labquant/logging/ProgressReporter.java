package edu.purdue.rochetlab.labquant.logging;

public interface ProgressReporter {
    void report(String message, int completed, int total);

    void done(String message);
}

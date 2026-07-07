package edu.purdue.rochetlab.labquant.logging;

import edu.purdue.rochetlab.labquant.core.WorkflowSummary;
import ij.IJ;

public class FijiStatusProgressReporter implements ProgressReporter {
    @Override
    public void report(String message, int completed, int total) {
        IJ.showStatus(message);
        if (total > 0) {
            IJ.showProgress(completed, total);
        }
    }

    @Override
    public void done(String message) {
        IJ.showStatus(message);
        IJ.showProgress(1.0);
    }

    public static void showFinished(WorkflowSummary summary) {
        IJ.showStatus("LabQuant finished: " + summary.getCompletedImages()
                + " completed, " + summary.getFailedImages() + " failed");
    }
}

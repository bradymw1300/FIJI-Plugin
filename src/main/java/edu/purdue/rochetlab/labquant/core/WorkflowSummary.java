package edu.purdue.rochetlab.labquant.core;

public class WorkflowSummary {
    private final int discoveredImages;
    private final int completedImages;
    private final int failedImages;
    private final boolean cancelled;

    public WorkflowSummary(int discoveredImages, int completedImages, int failedImages, boolean cancelled) {
        this.discoveredImages = discoveredImages;
        this.completedImages = completedImages;
        this.failedImages = failedImages;
        this.cancelled = cancelled;
    }

    public int getDiscoveredImages() {
        return discoveredImages;
    }

    public int getCompletedImages() {
        return completedImages;
    }

    public int getFailedImages() {
        return failedImages;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}

package edu.purdue.rochetlab.labquant.core;

public interface CancellationToken {
    boolean isCancellationRequested();

    static CancellationToken none() {
        return new CancellationToken() {
            @Override
            public boolean isCancellationRequested() {
                return false;
            }
        };
    }
}

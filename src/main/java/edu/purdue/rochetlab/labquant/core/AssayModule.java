package edu.purdue.rochetlab.labquant.core;

import edu.purdue.rochetlab.labquant.images.ImageContext;
import edu.purdue.rochetlab.labquant.results.AssayResult;

public interface AssayModule {
    String id();

    String displayName();

    AssayResult analyze(ImageContext imageContext, AssayConfig config) throws AssayException;
}

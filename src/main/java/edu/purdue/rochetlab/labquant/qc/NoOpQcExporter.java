package edu.purdue.rochetlab.labquant.qc;

import edu.purdue.rochetlab.labquant.images.ImageContext;
import java.io.File;

public class NoOpQcExporter implements QcExporter {
    @Override
    public void export(ImageContext imageContext, File qcDir) {
        // QC export hook intentionally left empty for the scaffold.
    }
}

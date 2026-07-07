package edu.purdue.rochetlab.labquant.qc;

import edu.purdue.rochetlab.labquant.images.ImageContext;
import java.io.File;

public interface QcExporter {
    void export(ImageContext imageContext, File qcDir) throws QcExportException;
}

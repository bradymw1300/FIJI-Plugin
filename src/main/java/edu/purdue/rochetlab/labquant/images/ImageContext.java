package edu.purdue.rochetlab.labquant.images;

import ij.ImagePlus;
import java.nio.file.Path;

public class ImageContext {
    private final Path sourcePath;
    private final ImagePlus imagePlus;
    private final CalibrationInfo calibrationInfo;

    public ImageContext(Path sourcePath, ImagePlus imagePlus) {
        this.sourcePath = sourcePath;
        this.imagePlus = imagePlus;
        this.calibrationInfo = CalibrationInfo.from(imagePlus);
    }

    public Path getSourcePath() {
        return sourcePath;
    }

    public ImagePlus getImagePlus() {
        return imagePlus;
    }

    public CalibrationInfo getCalibrationInfo() {
        return calibrationInfo;
    }
}

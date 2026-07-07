package edu.purdue.rochetlab.labquant.images;

import ij.ImagePlus;
import ij.measure.Calibration;

public class CalibrationInfo {
    private final double pixelWidthUm;
    private final double pixelHeightUm;

    public CalibrationInfo(double pixelWidthUm, double pixelHeightUm) {
        this.pixelWidthUm = pixelWidthUm;
        this.pixelHeightUm = pixelHeightUm;
    }

    public static CalibrationInfo from(ImagePlus imagePlus) {
        Calibration calibration = imagePlus.getCalibration();
        double width = calibration == null ? 1.0 : calibration.pixelWidth;
        double height = calibration == null ? 1.0 : calibration.pixelHeight;
        return new CalibrationInfo(width, height);
    }

    public double getPixelWidthUm() {
        return pixelWidthUm;
    }

    public double getPixelHeightUm() {
        return pixelHeightUm;
    }
}

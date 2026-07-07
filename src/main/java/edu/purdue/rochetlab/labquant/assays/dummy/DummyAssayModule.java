package edu.purdue.rochetlab.labquant.assays.dummy;

import edu.purdue.rochetlab.labquant.core.AssayConfig;
import edu.purdue.rochetlab.labquant.core.AssayException;
import edu.purdue.rochetlab.labquant.core.AssayModule;
import edu.purdue.rochetlab.labquant.images.CalibrationInfo;
import edu.purdue.rochetlab.labquant.images.ImageContext;
import edu.purdue.rochetlab.labquant.results.AssayResult;
import edu.purdue.rochetlab.labquant.results.MeasurementRecord;
import ij.ImagePlus;

public class DummyAssayModule implements AssayModule {
    @Override
    public String id() {
        return "dummy";
    }

    @Override
    public String displayName() {
        return "Dummy assay";
    }

    @Override
    public AssayResult analyze(ImageContext imageContext, AssayConfig config) throws AssayException {
        ImagePlus image = imageContext.getImagePlus();
        CalibrationInfo calibration = imageContext.getCalibrationInfo();
        AssayResult result = new AssayResult();
        result.addRecord(new MeasurementRecord()
                .put("source_path", imageContext.getSourcePath().toString())
                .put("image_title", image.getTitle())
                .put("width_px", image.getWidth())
                .put("height_px", image.getHeight())
                .put("channels", image.getNChannels())
                .put("slices", image.getNSlices())
                .put("frames", image.getNFrames())
                .put("bit_depth", image.getBitDepth())
                .put("pixel_width_um", calibration.getPixelWidthUm())
                .put("pixel_height_um", calibration.getPixelHeightUm()));
        return result;
    }
}

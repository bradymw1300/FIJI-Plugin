package edu.purdue.rochetlab.labquant.io;

import ij.IJ;
import ij.ImagePlus;
import java.nio.file.Path;

public class BasicImageLoaderService implements ImageLoaderService {
    @Override
    public ImagePlus open(Path sourcePath) throws ImageOpenException {
        ImagePlus imagePlus = IJ.openImage(sourcePath.toString());
        if (imagePlus == null) {
            throw new ImageOpenException("ImageJ could not open image");
        }
        return imagePlus;
    }
}

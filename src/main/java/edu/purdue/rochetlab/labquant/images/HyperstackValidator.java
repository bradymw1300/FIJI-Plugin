package edu.purdue.rochetlab.labquant.images;

import ij.ImagePlus;

public class HyperstackValidator {
    public boolean isOpenableImage(ImagePlus imagePlus) {
        return imagePlus != null && imagePlus.getWidth() > 0 && imagePlus.getHeight() > 0;
    }
}

package edu.purdue.rochetlab.labquant.io;

import ij.ImagePlus;
import java.nio.file.Path;

public interface ImageLoaderService {
    ImagePlus open(Path sourcePath) throws ImageOpenException;
}

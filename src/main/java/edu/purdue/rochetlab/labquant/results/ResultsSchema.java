package edu.purdue.rochetlab.labquant.results;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ResultsSchema {
    public static final List<String> DUMMY_COLUMNS = Collections.unmodifiableList(Arrays.asList(
            "source_path",
            "image_title",
            "width_px",
            "height_px",
            "channels",
            "slices",
            "frames",
            "bit_depth",
            "pixel_width_um",
            "pixel_height_um"));

    private ResultsSchema() {
    }
}

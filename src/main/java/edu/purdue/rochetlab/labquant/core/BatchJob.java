package edu.purdue.rochetlab.labquant.core;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BatchJob {
    private final List<ImageJob> imageJobs;

    public BatchJob(List<Path> paths) {
        this.imageJobs = new ArrayList<ImageJob>();
        for (Path path : paths) {
            this.imageJobs.add(new ImageJob(path));
        }
    }

    public List<ImageJob> getImageJobs() {
        return Collections.unmodifiableList(imageJobs);
    }
}

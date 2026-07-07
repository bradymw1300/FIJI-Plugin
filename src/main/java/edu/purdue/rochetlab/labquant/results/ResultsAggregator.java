package edu.purdue.rochetlab.labquant.results;

import edu.purdue.rochetlab.labquant.core.ImageFailure;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultsAggregator {
    private final List<MeasurementRecord> records = new ArrayList<MeasurementRecord>();
    private final List<ImageFailure> failures = new ArrayList<ImageFailure>();

    public void add(AssayResult result) {
        records.addAll(result.getRecords());
    }

    public void addFailure(ImageFailure failure) {
        failures.add(failure);
    }

    public List<MeasurementRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

    public List<ImageFailure> getFailures() {
        return Collections.unmodifiableList(failures);
    }
}

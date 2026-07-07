package edu.purdue.rochetlab.labquant.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AssayResult {
    private final List<MeasurementRecord> records = new ArrayList<MeasurementRecord>();

    public void addRecord(MeasurementRecord record) {
        records.add(record);
    }

    public List<MeasurementRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }
}

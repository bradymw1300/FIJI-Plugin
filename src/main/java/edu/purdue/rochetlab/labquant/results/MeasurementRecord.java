package edu.purdue.rochetlab.labquant.results;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class MeasurementRecord {
    private final Map<String, String> values = new LinkedHashMap<String, String>();

    public MeasurementRecord put(String key, Object value) {
        values.put(key, value == null ? "" : String.valueOf(value));
        return this;
    }

    public Map<String, String> values() {
        return Collections.unmodifiableMap(values);
    }
}

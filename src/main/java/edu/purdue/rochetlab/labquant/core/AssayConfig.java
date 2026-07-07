package edu.purdue.rochetlab.labquant.core;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class AssayConfig {
    private final Map<String, String> values;

    public AssayConfig(Map<String, String> values) {
        this.values = new LinkedHashMap<String, String>(values);
    }

    public static AssayConfig empty() {
        return new AssayConfig(Collections.<String, String>emptyMap());
    }

    public Map<String, String> values() {
        return Collections.unmodifiableMap(values);
    }
}

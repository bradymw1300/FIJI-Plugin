package edu.purdue.rochetlab.labquant.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PreflightReport {
    private final List<String> warnings = new ArrayList<String>();

    public void addWarning(String warning) {
        warnings.add(warning);
    }

    public List<String> getWarnings() {
        return Collections.unmodifiableList(warnings);
    }

    public boolean hasWarnings() {
        return !warnings.isEmpty();
    }
}

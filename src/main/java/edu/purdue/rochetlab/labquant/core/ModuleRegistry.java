package edu.purdue.rochetlab.labquant.core;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModuleRegistry {
    private final Map<String, AssayModule> modules = new LinkedHashMap<String, AssayModule>();

    public void register(AssayModule module) {
        modules.put(module.id(), module);
    }

    public AssayModule require(String id) throws AssayException {
        AssayModule module = modules.get(id);
        if (module == null) {
            throw new AssayException("No assay module registered for id: " + id);
        }
        return module;
    }
}

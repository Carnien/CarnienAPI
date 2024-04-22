package net.carnien.api.input;

import java.util.HashMap;
import java.util.Map;

public class ModuleManager {

    private final Map<String, CarnienModule> modules = new HashMap<>();

    public void enableAll() {
        for (CarnienModule module : modules.values()) module.enable();
    }

    public void add(CarnienModule module) {
        final String name = module.getName().toLowerCase();
        if (!modules.containsKey(name)) modules.put(name, module);
    }

    public void remove(String name) {
        modules.remove(name);
    }

    public CarnienModule get(String name) {
        name = name.toLowerCase();
        return modules.get(name);
    }

}

package net.carnien.api.input;

import java.util.HashMap;
import java.util.Map;

public class ModuleManager {

    private final Map<String, Module> modules = new HashMap<>();

    public void enableAll() {
        for (Module module : modules.values()) {
            module.enable();
        }
    }

    public void add(String name, Module module) {
        if (!modules.containsKey(name)) modules.put(name, module);
    }

    public void remove(String name) {
        if (modules.containsKey(name)) modules.remove(name);
    }

    public Module get(String name) {
        return modules.get(name);
    }

}

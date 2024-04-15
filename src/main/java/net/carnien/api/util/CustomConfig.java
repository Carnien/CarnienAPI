package net.carnien.api.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class CustomConfig {

    public static File create(JavaPlugin plugin, String name) {
        final File pluginDirectory = plugin.getDataFolder();
        final File customFile = new File(pluginDirectory, name);

        if (!customFile.exists()) {
            customFile.getParentFile().mkdirs();
            plugin.saveResource(name, false);
        }

        return customFile;
    }

    public static FileConfiguration get(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void save(File file, FileConfiguration config) {
        try { config.save(file); } catch (IOException ignored) { }
    }

}

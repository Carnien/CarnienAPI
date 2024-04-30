package net.carnien.api.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class CustomConfig {

    /**
     * Creates a custom yaml configuration file.
     * File will be generated in plugin's directory
     *
     * @param plugin Owner of the file.
     * @param name Name of the file. No need to end it like ".yml"
     * @return The file which has been created.
     */
    public static File create(JavaPlugin plugin, String name) {
        final File pluginDirectory = plugin.getDataFolder();
        final File customFile = new File(pluginDirectory, name);

        if (!customFile.exists()) {
            customFile.getParentFile().mkdirs();
            plugin.saveResource(name, false);
        }

        return customFile;
    }


    /**
     * Loads the content of a file into a
     * FileConfiguration.
     *
     * @param file File to load.
     * @return A loaded file converted into a FileConfiguration
     */
    public static FileConfiguration get(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Saves a FileConfiguration into a file.
     *
     * @param file The file to save.
     * @param config The FileConfiguration with all the content.
     */
    public static void save(File file, FileConfiguration config) {
        try { config.save(file); } catch (IOException ignored) { }
    }

}

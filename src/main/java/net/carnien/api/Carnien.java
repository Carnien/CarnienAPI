package net.carnien.api;

import net.carnien.api.input.CarnienModule;
import net.carnien.api.input.ModuleManager;
import net.carnien.api.input.command.GamemodeCommand;
import net.carnien.api.input.command.TeleportCommand;
import net.carnien.api.input.module.essential.carnienlogger.CarnienLogger;
import net.carnien.api.input.module.essential.commandhandler.CommandHandler;
import net.carnien.api.input.module.essential.permissionmanager.PermissionManager;
import net.carnien.api.input.module.optional.whitelistmanager.WhitelistManager;
import net.carnien.dependencymanager.Dependency;
import net.carnien.dependencymanager.DependencyManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Carnien extends JavaPlugin {

    private final DependencyManager dependencyManager = new DependencyManager();
    private final ModuleManager staticModuleManager = new ModuleManager();
    private final ModuleManager optionalModuleManager = new ModuleManager();

    @Override
    public void onLoad() {
        initializeDependencies();
        dependencyManager.downloadMissingDependencies();
    }

    private void initializeDependencies() {
        dependencyManager.add(new Dependency("ProtocolLib",
            "https://ci.dmulloy2.net/job/ProtocolLib/lastBuild/artifact/build/libs/ProtocolLib.jar"));
    }

    @Override
    public void onEnable() {
        if (!dependencyManager.isReloadRequired()) {
            initialize();
            return;
        }

        final Logger logger = Bukkit.getLogger();
        logger.warning("Please restart or reload the server to enable downloaded dependencies!");
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.disablePlugin(this);
    }

    private void initialize() {
        saveDefaultConfig();
        initializeStaticModules();
        initializeCommands();
        initializeOptionalModules();
    }

    private void initializeCommands() {
        getCommandHandler().add(new GamemodeCommand(this));
        getCommandHandler().add(new TeleportCommand(this));
    }

    private void initializeStaticModules() {
        staticModuleManager.add(new CarnienLogger(this));
        staticModuleManager.add(new CommandHandler(this));
        staticModuleManager.add(new PermissionManager(this));
        staticModuleManager.enableAll();
    }

    private void initializeOptionalModules() {
        optionalModuleManager.add(new WhitelistManager(this));
        optionalModuleManager.enableAll();
    }

    public void addModule(CarnienModule module) {
        optionalModuleManager.add(module);
    }

    public void removeModule(String name) {
        optionalModuleManager.remove(name);
    }

    public CarnienModule getModule(String name) {
        return optionalModuleManager.get(name);
    }

    public CarnienLogger getCarnienLogger() {
        return (CarnienLogger) staticModuleManager.get("CarnienLogger");
    }

    public CommandHandler getCommandHandler() {
        return (CommandHandler) staticModuleManager.get("CommandHandler");
    }

    public PermissionManager getPermissionManager() {
        return (PermissionManager) staticModuleManager.get("PermissionManager");
    }

}

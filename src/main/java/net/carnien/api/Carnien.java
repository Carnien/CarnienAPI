package net.carnien.api;

import net.carnien.api.input.command.*;
import net.carnien.api.input.module.*;
import net.carnien.api.input.ModuleManager;
import net.carnien.api.input.Module;
import net.carnien.dependencymanager.Dependency;
import net.carnien.dependencymanager.DependencyManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Carnien extends JavaPlugin {

    private final DependencyManager dependencyManager = new DependencyManager();
    private final ModuleManager moduleManager = new ModuleManager();

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
        initializeModules();
        moduleManager.enableAll();
        initializeCommands();
        getPermissionManager().saveDefaultPermissions();
        getPermissionManager().createTeams();
    }

    private void initializeModules() {
        moduleManager.add("CarnienLogger", new CarnienLogger(this));
        moduleManager.add("CommandHandler", new CommandHandler(this));
        moduleManager.add("PermissionManager", new PermissionManager(this));
        moduleManager.add("JsonHelper", new JsonHelper(this));
        moduleManager.add("PlayerHelper", new PlayerHelper(this));
        moduleManager.add("MojangApi", new MojangApi(this));
        moduleManager.add("WhitelistManager", new WhitelistManager(this));
    }

    private void initializeCommands() {
        final CommandHandler commandHandler = getCommandHandler();
        commandHandler.add("group", new GroupCommand(this));
        commandHandler.add("groups", new GroupsCommand(this));
        commandHandler.add("gamemode", new GamemodeCommand(this));
        commandHandler.add("teleport", new TeleportCommand(this));
        commandHandler.add("whitelist", new WhitelistCommand(this));
    }

    public Module getModule(String name) {
        return moduleManager.get(name);
    }

    public void addModule(String name, Module module) {
        moduleManager.add(name, module);
    }

    public void removeModule(String name) {
        moduleManager.remove(name);
    }

    public CarnienLogger getCarnienLogger() {
        return (CarnienLogger) moduleManager.get("CarnienLogger");
    }

    public PermissionManager getPermissionManager() {
        return (PermissionManager) moduleManager.get("PermissionManager");
    }

    public CommandHandler getCommandHandler() {
        return (CommandHandler) moduleManager.get("CommandHandler");
    }

}

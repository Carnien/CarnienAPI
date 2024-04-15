package net.carnien.api;

import net.carnien.api.input.module.CommandHandler;
import net.carnien.api.input.ModuleManager;
import net.carnien.api.input.command.GamemodeCommand;
import net.carnien.api.input.command.GroupCommand;
import net.carnien.api.input.command.GroupsCommand;
import net.carnien.api.input.module.CarnienLogger;
import net.carnien.api.input.module.JsonHelper;
import net.carnien.api.input.module.MojangApi;
import net.carnien.api.input.module.PlayerHelper;
import net.carnien.api.input.module.PermissionManager;
import net.carnien.api.input.Module;
import org.bukkit.plugin.java.JavaPlugin;

public final class Carnien extends JavaPlugin {

    private final ModuleManager moduleManager = new ModuleManager();

    @Override
    public void onEnable() {
        initialize();
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
    }

    private void initializeCommands() {
        final CommandHandler commandHandler = getCommandHandler();
        commandHandler.add("group", new GroupCommand(this));
        commandHandler.add("groups", new GroupsCommand(this));
        commandHandler.add("gamemode", new GamemodeCommand(this));
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

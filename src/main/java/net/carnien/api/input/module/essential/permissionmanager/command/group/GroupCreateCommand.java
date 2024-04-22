package net.carnien.api.input.module.essential.permissionmanager.command.group;

import net.carnien.api.Carnien;
import net.carnien.api.input.module.essential.permissionmanager.Group;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.module.essential.permissionmanager.PermissionManager;
import net.carnien.api.input.module.essential.carnienlogger.CarnienLogger;
import org.bukkit.command.CommandSender;

public class GroupCreateCommand extends CarnienCommand {

    public GroupCreateCommand(Carnien carnien) {
        super(carnien, "create", "api.group.create", "group create <group-name>");
        setArgs(1);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        final CarnienLogger logger = getCarnien().getCarnienLogger();
        final String groupName = args[0];
        final PermissionManager permissionManager = getCarnien().getPermissionManager();

        if (permissionManager.groupExists(groupName)) {
            final Group group = permissionManager.getGroup(groupName);
            logger.sendError(sender, "Group named " + group.getColor() + group.getName() + " §cdoes already exist.");
            return;
        }

        permissionManager.createGroup(groupName);
        final Group group = permissionManager.getGroup(groupName);
        logger.sendMessage(sender, "§aGroup named " + group.getColor() + group.getName() + " §ahas been created successfully.");
    }

}

package net.carnien.api.input.module.essential.permissionmanager.command.group;

import net.carnien.api.Carnien;
import net.carnien.api.input.module.essential.permissionmanager.Group;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.module.essential.permissionmanager.PermissionManager;
import net.carnien.api.input.module.essential.carnienlogger.CarnienLogger;
import org.bukkit.command.CommandSender;

public class GroupDeleteCommand extends CarnienCommand {

    public GroupDeleteCommand(Carnien carnien) {
        super(carnien, "delete", "api.group.delete", "group delete <group-name>");
        setArgs(1);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        final CarnienLogger logger = getCarnien().getCarnienLogger();
        final String groupName = args[0].toLowerCase();
        final PermissionManager permissionManager = getCarnien().getPermissionManager();
        final Group group = permissionManager.getGroup(groupName);

        if (group == null) {
            logger.sendError(sender, "Group named §4" + args[0] + " §cdoes not exist.");
            return;
        }

        permissionManager.deleteGroup(groupName);
        logger.sendMessage(sender, "§aGroup " + group.getColor() + group.getName() + " §ahas been deleted.");
    }

}
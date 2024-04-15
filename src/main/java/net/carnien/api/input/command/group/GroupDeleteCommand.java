package net.carnien.api.input.command.group;

import net.carnien.api.Carnien;
import net.carnien.api.input.Command;
import net.carnien.api.permission.Group;
import net.carnien.api.input.module.PermissionManager;
import net.carnien.api.input.module.CarnienLogger;
import org.bukkit.command.CommandSender;

public class GroupDeleteCommand extends Command {

    public GroupDeleteCommand(Carnien carnien) {
        super(carnien, "Group Delete", "api.group.delete", "group delete <group-name>");
        setMinArgs(1);
        setMaxArgs(1);
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
package net.carnien.api.input.command.group.permission;

import net.carnien.api.Carnien;
import net.carnien.api.input.Command;
import net.carnien.api.permission.Group;
import net.carnien.api.input.module.PermissionManager;
import net.carnien.api.input.module.CarnienLogger;
import org.bukkit.command.CommandSender;

import java.util.List;

public class GroupPermissionRemoveCommand extends Command {

    public GroupPermissionRemoveCommand(Carnien carnien) {
        super(carnien, "Group Permission Remove", "api.group.permission.remove",
            "group permission remove <group-name> <permission ...>");
        setMinArgs(2);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        final CarnienLogger logger = getCarnien().getCarnienLogger();
        final String groupName = args[0];
        final PermissionManager permissionManager = getCarnien().getPermissionManager();
        final Group group = permissionManager.getGroup(groupName);

        if (group == null) {
            logger.sendError(sender, "Group named §4" + groupName + " §cdoes not exist.");
            return;
        }

        final List<String> permissions = group.getPermissions();

        for (int i = 1 ; i < args.length; i++) {
            final String permission = args[i].toLowerCase();

            if (!permissions.contains(permission)) {
                logger.sendError(sender, "Group " + group.getColor() + group.getName() +
                    " §cis not permitted by §4" + permission + "§c.");
                continue;
            }

            permissionManager.removePermission(group, permission);
            logger.sendMessage(sender, "§aGroup " + group.getColor() + group.getName() +
                " §ais not longer permitted by §e" + permission + "§a.");
        }
    }

}

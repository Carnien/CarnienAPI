package net.carnien.api.input.module.essential.permissionmanager.command.group.permission;

import net.carnien.api.Carnien;
import net.carnien.api.input.module.essential.permissionmanager.Group;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.module.essential.permissionmanager.PermissionManager;
import net.carnien.api.input.module.essential.carnienlogger.CarnienLogger;
import org.bukkit.command.CommandSender;

import java.util.List;

public class GroupPermissionRemoveCommand extends CarnienCommand {

    public GroupPermissionRemoveCommand(Carnien carnien) {
        super(carnien, "remove", "api.group.permission.remove",
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

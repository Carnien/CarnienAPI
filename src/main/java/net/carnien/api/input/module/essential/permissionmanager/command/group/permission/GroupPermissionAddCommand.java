package net.carnien.api.input.module.essential.permissionmanager.command.group.permission;

import net.carnien.api.Carnien;
import net.carnien.api.input.module.essential.permissionmanager.Group;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.module.essential.permissionmanager.PermissionManager;
import net.carnien.api.input.module.essential.carnienlogger.CarnienLogger;
import org.bukkit.command.CommandSender;

import java.util.List;

public class GroupPermissionAddCommand extends CarnienCommand {

    public GroupPermissionAddCommand(Carnien carnien) {
        super(carnien, "add", "api.group.permission.add",
            "group permission add <group-name> <permission ...>");
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

            if (permissions.contains(permission)) {
                logger.sendError(sender, "Group " + group.getColor() + group.getName() +
                    " §cis already permitted by §4" + permission + "§c.");
                continue;
            }

            permissionManager.addPermission(group, permission);
            logger.sendMessage(sender, "§aGroup " + group.getColor() + group.getName() +
                " §ais now permitted by §e" + permission + "§a.");
        }
    }

}

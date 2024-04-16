package net.carnien.api.input.command;

import net.carnien.api.Carnien;
import net.carnien.api.input.Command;
import net.carnien.api.permission.Group;
import net.carnien.api.input.module.PermissionManager;
import net.carnien.api.input.module.CarnienLogger;
import org.bukkit.command.CommandSender;

import java.util.List;

public class GroupsCommand extends Command {

    public GroupsCommand(Carnien carnien) {
        super(carnien, "Groups", "api.groups", "groups");
        setMaxArgs(0);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        final CarnienLogger logger = getCarnien().getCarnienLogger();
        final PermissionManager permissionManager = getCarnien().getPermissionManager();
        final List<Group> groups = permissionManager.getGroups();
        String message = "§fGroups (§a" + groups.size() + "§f): ";

        if (groups.isEmpty()) {
            logger.sendMessage(sender, message);
            return;
        }

        final Group firstGroup = groups.get(0);
        final StringBuilder groupsStr = new StringBuilder(firstGroup.getColor() + firstGroup.getName());

        for (int i = 1; i < groups.size(); i++) {
            final Group group = groups.get(i);
            groupsStr.append("§f, ");
            groupsStr.append(group.getColor() + group.getName());
        }

        message += groupsStr;
        logger.sendMessage(sender, message);
    }

}

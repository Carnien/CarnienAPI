package net.carnien.api.input.module.essential.permissionmanager.command;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.module.essential.permissionmanager.command.group.GroupCreateCommand;
import net.carnien.api.input.module.essential.permissionmanager.command.group.GroupDeleteCommand;
import net.carnien.api.input.module.essential.permissionmanager.command.group.GroupPermissionCommand;
import net.carnien.api.input.module.essential.permissionmanager.command.group.GroupSetCommand;

public class GroupCommand extends CarnienCommand {

    public GroupCommand(Carnien carnien) {
        super(carnien, "group", "api.group", "group");
        addSubCommand(new GroupCreateCommand(carnien));
        addSubCommand(new GroupSetCommand(carnien));
        addSubCommand(new GroupPermissionCommand(carnien));
        addSubCommand(new GroupDeleteCommand(carnien));
    }

}

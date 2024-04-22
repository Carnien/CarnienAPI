package net.carnien.api.input.module.essential.permissionmanager.command.group;

import net.carnien.api.Carnien;
import net.carnien.api.input.module.essential.permissionmanager.command.group.permission.GroupPermissionAddCommand;
import net.carnien.api.input.module.essential.permissionmanager.command.group.permission.GroupPermissionRemoveCommand;
import net.carnien.api.input.CarnienCommand;

public class GroupPermissionCommand extends CarnienCommand {

    public GroupPermissionCommand(Carnien carnien) {
        super(carnien, "permission", "api.group.permission", "group permission");
        addSubCommand(new GroupPermissionAddCommand(carnien));
        addSubCommand(new GroupPermissionRemoveCommand(carnien));
    }

}

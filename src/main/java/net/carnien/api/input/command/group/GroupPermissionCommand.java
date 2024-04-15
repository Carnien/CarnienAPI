package net.carnien.api.input.command.group;

import net.carnien.api.Carnien;
import net.carnien.api.input.Command;
import net.carnien.api.input.command.group.permission.GroupPermissionAddCommand;
import net.carnien.api.input.command.group.permission.GroupPermissionRemoveCommand;

public class GroupPermissionCommand extends Command {

    public GroupPermissionCommand(Carnien carnien) {
        super(carnien, "Group Permission", "api.group.permission", "group permission");
    }

    @Override
    protected void registerSubCommands() {
        addSubCommand("add", new GroupPermissionAddCommand(getCarnien()));
        addSubCommand("remove", new GroupPermissionRemoveCommand(getCarnien()));
    }

}

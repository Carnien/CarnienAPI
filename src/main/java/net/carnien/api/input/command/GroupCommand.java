package net.carnien.api.input.command;

import net.carnien.api.Carnien;
import net.carnien.api.input.Command;
import net.carnien.api.input.command.group.GroupCreateCommand;
import net.carnien.api.input.command.group.GroupDeleteCommand;
import net.carnien.api.input.command.group.GroupPermissionCommand;
import net.carnien.api.input.command.group.GroupSetCommand;

public class GroupCommand extends Command {

    public GroupCommand(Carnien carnien) {
        super(carnien, "Group", "api.group", "group");
        addSubCommand("create", new GroupCreateCommand(getCarnien()));
        addSubCommand("set", new GroupSetCommand(getCarnien()));
        addSubCommand("permission", new GroupPermissionCommand(getCarnien()));
        addSubCommand("delete", new GroupDeleteCommand(getCarnien()));
    }

}

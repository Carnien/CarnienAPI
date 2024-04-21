package net.carnien.api.input.command;

import net.carnien.api.Carnien;
import net.carnien.api.input.Command;
import net.carnien.api.input.command.whitelist.WhitelistAddCommand;

public class WhitelistCommand extends Command {

    public WhitelistCommand(Carnien carnien) {
        super(carnien, "Whitelist", "api.whitelist", "/whitelist");
        addSubCommand("add", new WhitelistAddCommand(getCarnien()));
    }

}

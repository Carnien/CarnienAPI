package net.carnien.api.input.command.whitelist;

import net.carnien.api.Carnien;
import net.carnien.api.input.Command;
import net.carnien.api.input.command.whitelist.add.WhitelistAddNameCommand;

public class WhitelistAddCommand extends Command {

    public WhitelistAddCommand(Carnien carnien) {
        super(carnien, "Whitelist Add", "api.whitelist.add", "/whitelist add");
        addSubCommand("name", new WhitelistAddNameCommand(getCarnien()));
    }

}

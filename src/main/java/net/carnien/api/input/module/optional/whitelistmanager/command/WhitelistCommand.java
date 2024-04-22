package net.carnien.api.input.module.optional.whitelistmanager.command;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.module.optional.whitelistmanager.WhitelistManager;
import net.carnien.api.input.module.optional.whitelistmanager.command.whitelist.WhitelistAddCommand;

public class WhitelistCommand extends CarnienCommand {

    public WhitelistCommand(Carnien carnien, WhitelistManager whitelistManager) {
        super(carnien, "whitelist", "api.whitelist", "whitelist");
        addSubCommand(new WhitelistAddCommand(carnien, whitelistManager));
    }

}

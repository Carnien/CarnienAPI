package net.carnien.api.input.module.optional.whitelistmanager.command.whitelist;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.module.optional.whitelistmanager.WhitelistManager;
import net.carnien.api.input.module.optional.whitelistmanager.command.whitelist.add.WhitelistAddNameCommand;

public class WhitelistAddCommand extends CarnienCommand {

    public WhitelistAddCommand(Carnien carnien, WhitelistManager whitelistManager) {
        super(carnien, "add", "api.whitelist.add", "whitelist add");
        addSubCommand(new WhitelistAddNameCommand(carnien, whitelistManager));
    }

}

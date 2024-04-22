package net.carnien.api.input.module.optional.whitelistmanager.command.whitelist;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.module.optional.whitelistmanager.WhitelistManager;
import net.carnien.api.input.module.optional.whitelistmanager.command.whitelist.remove.WhitelistRemoveIpCommand;
import net.carnien.api.input.module.optional.whitelistmanager.command.whitelist.remove.WhitelistRemoveNameCommand;
import net.carnien.api.input.module.optional.whitelistmanager.command.whitelist.remove.WhitelistRemoveUuidCommand;

public class WhitelistRemoveCommand extends CarnienCommand {

    public WhitelistRemoveCommand(Carnien carnien, WhitelistManager whitelistManager) {
        super(carnien, "remove", "api.whitelist.remove", "whitelist remove");
        addSubCommand(new WhitelistRemoveNameCommand(carnien, whitelistManager));
        addSubCommand(new WhitelistRemoveUuidCommand(carnien, whitelistManager));
        addSubCommand(new WhitelistRemoveIpCommand(carnien, whitelistManager));
    }

}

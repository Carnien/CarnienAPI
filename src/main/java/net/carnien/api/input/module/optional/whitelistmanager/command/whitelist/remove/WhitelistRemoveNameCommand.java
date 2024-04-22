package net.carnien.api.input.module.optional.whitelistmanager.command.whitelist.remove;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.module.essential.carnienlogger.CarnienLogger;
import net.carnien.api.input.module.optional.whitelistmanager.WhitelistManager;
import org.bukkit.command.CommandSender;

public class WhitelistRemoveNameCommand extends CarnienCommand {

    private final WhitelistManager whitelistManager;

    public  WhitelistRemoveNameCommand(Carnien carnien, WhitelistManager whitelistManager) {
        super(carnien, "name", "api.whitelist.remove.name", "whitelist remove name <player ...>");
        this.whitelistManager = whitelistManager;
        setMinArgs(1);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        final CarnienLogger logger = getCarnien().getCarnienLogger();

        for (String name : args) {
            name = name.toLowerCase();

            if (!whitelistManager.isPlayerNameListed(name)) {
                logger.sendError(sender, "Name §4" + name + " §cis not whitelisted.");
                continue;
            }

            whitelistManager.removePlayerName(name);
            logger.sendMessage(sender, "§aName §e" + name + " §ais not longer whitelisted.");
        }
    }

}

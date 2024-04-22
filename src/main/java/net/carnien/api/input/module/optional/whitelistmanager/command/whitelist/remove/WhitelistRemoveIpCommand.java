package net.carnien.api.input.module.optional.whitelistmanager.command.whitelist.remove;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.module.essential.carnienlogger.CarnienLogger;
import net.carnien.api.input.module.optional.whitelistmanager.WhitelistManager;
import org.bukkit.command.CommandSender;

public class WhitelistRemoveIpCommand extends CarnienCommand {

    private final WhitelistManager whitelistManager;

    public  WhitelistRemoveIpCommand(Carnien carnien, WhitelistManager whitelistManager) {
        super(carnien, "ip", "api.whitelist.remove.ip", "whitelist remove ip <player ...>");
        this.whitelistManager = whitelistManager;
        setMinArgs(1);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        final CarnienLogger logger = getCarnien().getCarnienLogger();

        for (String ipAddress : args) {
            if (!whitelistManager.isIpAddressListed(ipAddress)) {
                logger.sendError(sender, "Name §4" + ipAddress + " §cis not whitelisted.");
                continue;
            }

            whitelistManager.removeIpAddress(ipAddress);
            logger.sendMessage(sender, "§aName §e" + ipAddress + " §ais not longer whitelisted.");
        }
    }

}

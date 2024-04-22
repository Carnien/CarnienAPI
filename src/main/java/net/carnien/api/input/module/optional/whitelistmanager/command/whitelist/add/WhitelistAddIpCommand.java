package net.carnien.api.input.module.optional.whitelistmanager.command.whitelist.add;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.module.essential.carnienlogger.CarnienLogger;
import net.carnien.api.input.module.optional.whitelistmanager.WhitelistManager;
import org.bukkit.command.CommandSender;

public class WhitelistAddIpCommand extends CarnienCommand {

    private final WhitelistManager whitelistManager;

    public WhitelistAddIpCommand(Carnien carnien, WhitelistManager whitelistManager) {
        super(carnien, "ip", "api.whitelist.add.ip", "whitelist add ip <ip-address ...>");
        this.whitelistManager = whitelistManager;
        setMinArgs(1);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        final CarnienLogger logger = getCarnien().getCarnienLogger();

        for (String ipAddress : args) {
            if (whitelistManager.isIpAddressListed(ipAddress)) {
                logger.sendError(sender, "Ip-Address §4" + ipAddress + " §cis already whitelisted.");
                continue;
            }

            whitelistManager.addIpAddress(ipAddress);
            logger.sendMessage(sender, "§aIp-Address §e" + ipAddress + " §ais now whitelisted.");
        }
    }

}

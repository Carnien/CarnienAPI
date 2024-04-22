package net.carnien.api.input.module.optional.whitelistmanager.command.whitelist.remove;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.module.essential.carnienlogger.CarnienLogger;
import net.carnien.api.input.module.optional.whitelistmanager.WhitelistManager;
import org.bukkit.command.CommandSender;

public class WhitelistRemoveUuidCommand extends CarnienCommand {

    private final WhitelistManager whitelistManager;

    public WhitelistRemoveUuidCommand(Carnien carnien, WhitelistManager whitelistManager) {
        super(carnien, "uuid", "api.whitelist.remove.uuid", "whitelist remove uuid <uuid ...>");
        this.whitelistManager = whitelistManager;
        setMinArgs(1);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        final CarnienLogger logger = getCarnien().getCarnienLogger();

        for (String uuid : args) {
            if (!whitelistManager.isUuidListed(uuid)) {
                logger.sendError(sender, "Uuid §4" + uuid + " §cis not whitelisted.");
                continue;
            }

            whitelistManager.removeUuid(uuid);
            logger.sendMessage(sender, "§aName §e" + uuid + " §ais not longer whitelisted.");
        }
    }

}
package net.carnien.api.input.module.optional.whitelistmanager.command.whitelist.add;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.module.essential.carnienlogger.CarnienLogger;
import net.carnien.api.input.module.optional.whitelistmanager.WhitelistManager;
import org.bukkit.command.CommandSender;

public class WhitelistAddNameCommand extends CarnienCommand {

    private final WhitelistManager whitelistManager;

    public WhitelistAddNameCommand(Carnien carnien, WhitelistManager whitelistManager) {
        super(carnien, "name", "api.whitelist.add.name", "whitelist add name <player ...>");
        this.whitelistManager = whitelistManager;
        setMinArgs(1);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        final CarnienLogger logger = getCarnien().getCarnienLogger();

        for (String arg : args) {
            final String name = arg.toLowerCase();
            if (whitelistManager.isPlayerNameListed(name)) {
                logger.sendError(sender, "Name §4" + name + " §cis already whitelisted.");
                continue;
            }

            whitelistManager.addPlayerName(name);
            logger.sendMessage(sender, "§aName §e" + name + " §ais now whitelisted.");
        }
    }

}

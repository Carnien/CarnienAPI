package net.carnien.api.input.command.whitelist.add;

import net.carnien.api.Carnien;
import net.carnien.api.input.Command;
import net.carnien.api.input.module.CarnienLogger;
import net.carnien.api.input.module.WhitelistManager;
import org.bukkit.command.CommandSender;

public class WhitelistAddNameCommand extends Command {

    public WhitelistAddNameCommand(Carnien carnien) {
        super(carnien, "Whitelist Add Name", "api.whitelist.add.name", "/whitelist add name <player ...>");
        setMinArgs(1);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        final WhitelistManager whitelistManager = (WhitelistManager) getCarnien().getModule("WhitelistManager");
        final CarnienLogger logger = getCarnien().getCarnienLogger();

        for (int i = 0; i < args.length; i++) {
            final String name = args[i].toLowerCase();
            if (whitelistManager.isPlayerNameListed(name)) {
                logger.sendError(sender, "Name §4" + name + " §cis already whitelisted.");
                continue;
            }

            whitelistManager.addPlayerName(name);
            logger.sendMessage(sender, "§aName §e" + name + " §ais now whitelisted.");
        }
    }

}

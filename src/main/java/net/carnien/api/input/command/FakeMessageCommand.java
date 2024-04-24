package net.carnien.api.input.command;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.module.essential.carnienlogger.CarnienLogger;
import net.carnien.api.util.StringHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class FakeMessageCommand extends CarnienCommand {

    public FakeMessageCommand(Carnien carnien) {
        super(carnien, "fakemessage", "api.fakemessage", "fakemessage <player> <message>");
        addAlias("fakemsg");
        addAlias("fmsg");
        setMinArgs(2);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        final CarnienLogger logger = getCarnien().getCarnienLogger();
        final String playerName = args[0];
        final Player player = Bukkit.getPlayer(playerName);

        if (player == null) {
            logger.sendError(sender, "Player §4" + playerName + " §cis not online.");
            return;
        }

        final String[] words = Arrays.copyOfRange(args, 1, args.length);
        final String text = StringHelper.toSentence(words);
        logger.sendChatMessage(player, text);
    }

}

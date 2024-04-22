package net.carnien.api.input.module.essential.carnienlogger;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienModule;
import net.carnien.api.input.module.essential.permissionmanager.Group;
import net.carnien.api.input.module.essential.permissionmanager.PermissionManager;
import net.carnien.api.util.FormatType;
import net.carnien.api.util.Formatter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CarnienLogger extends CarnienModule {

    public CarnienLogger(Carnien carnien) {
        super(carnien, "CarnienLogger");
        addListener(new CarnienLoggerListener(carnien));
    }

    public void print(String text) {
        text = Formatter.format(FormatType.FILTER, text);
        System.out.println(text);
    }

    public void printError(String error) {
        print("ERROR: " + error);
    }

    public void sendMessage(CommandSender sender, String text) {
        final FileConfiguration config = getCarnien().getConfig();
        final String prefix = (String) config.get("prefix");
        String message = prefix + text;
        message = Formatter.format(FormatType.AND_TO_PARAGRAPH, message);
        sender.sendMessage(message);
    }

    public void sendChatMessage(Player player, String text) {
        final FileConfiguration config = getCarnien().getConfig();
        final PermissionManager permissionManager = getCarnien().getPermissionManager();
        final Group group = permissionManager.getRank(player);
        String prefix = (String) config.get("messages.player-message.syntax");
        prefix = prefix.replace("%player%", player.getName());
        prefix = prefix.replace("%rank%", group.getName());
        prefix = prefix.replace("%short-rank%", group.getShortName());
        prefix = prefix.replace("%rank-color%", group.getColor());
        prefix = prefix.replace("%message-color%", group.getMessageColor());
        prefix = Formatter.format(FormatType.AND_TO_PARAGRAPH, prefix);
        final String message = prefix + text;
        Bukkit.broadcastMessage(message);
    }

    public void broadcastMessage(String text) {
        final FileConfiguration config = getCarnien().getConfig();
        final String prefix = (String) config.get("prefix");
        String message = prefix + text;
        message = Formatter.format(FormatType.AND_TO_PARAGRAPH, message);
        Bukkit.broadcastMessage(message);
    }

    public void sendDebug(CommandSender sender, String debug) { sender.sendMessage("§e[DEBUG] §f" + debug);}

    public void sendError(CommandSender sender, String error) {
        sendMessage(sender, "§c" + error);
    }

    public void sendCommandNotExistError(CommandSender target, String command) {
        sendError(target, "Command §4" + command + " §cdoes not exist.");
    }

    public void sendUsageError(CommandSender target, String usage) {
        sendError(target, "Usage: " + usage);
    }

    public void sendPermissionDeniedError(CommandSender target) {
        sendError(target, "You got no permission to execute that command.");
    }

}

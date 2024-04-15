package net.carnien.api.input.module.carnienlogger.listener;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienListener;
import net.carnien.api.input.module.CarnienLogger;
import net.carnien.api.input.module.PermissionManager;
import net.carnien.api.permission.Group;
import net.carnien.api.util.FormatType;
import net.carnien.api.util.Formatter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CarnienLoggerListener extends CarnienListener {

    public CarnienLoggerListener(Carnien carnien) {
        super(carnien);
    }

    @EventHandler
    private void onAsyncPlayerChatListener(AsyncPlayerChatEvent event) {
        final FileConfiguration config = getCarnien().getConfig();
        final boolean isPlayerMessageEnabled = (boolean) config.get("messages.player-message.enabled");

        if (!isPlayerMessageEnabled) return;

        final Player player = event.getPlayer();
        final String message = event.getMessage();
        final CarnienLogger logger = getCarnien().getCarnienLogger();
        logger.sendChatMessage(player, message);
        event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        final FileConfiguration config = getCarnien().getConfig();
        final boolean isPlayerJoinMessageEnabled = (boolean) config.get("messages.join-message.enabled");

        if (!isPlayerJoinMessageEnabled) {
            event.setJoinMessage(null);
            return;
        }

        final Player player = event.getPlayer();
        final PermissionManager permissionManager = getCarnien().getPermissionManager();
        final Group group = permissionManager.getRank(player);
        String syntax = (String) config.get("messages.join-message.syntax");
        syntax = syntax.replace("%player%", player.getName());
        syntax = syntax.replace("%rank%", group.getName());
        syntax = syntax.replace("%rank-color%", group.getColor());
        syntax = syntax.replace("%short-rank%", group.getShortName());
        syntax = Formatter.format(FormatType.AND_TO_PARAGRAPH, syntax);
        event.setJoinMessage(syntax);
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        final FileConfiguration config = getCarnien().getConfig();
        final boolean isPlayerQuitMessageEnabled = (boolean) config.get("messages.quit-message.enabled");

        if (!isPlayerQuitMessageEnabled) {
            event.setQuitMessage(null);
            return;
        }

        final Player player = event.getPlayer();
        final PermissionManager permissionManager = getCarnien().getPermissionManager();
        final Group group = permissionManager.getRank(player);
        String syntax = (String) config.get("messages.quit-message.syntax");
        syntax = syntax.replace("%player%", player.getName());
        syntax = syntax.replace("%rank%", group.getName());
        syntax = syntax.replace("%rank-color%", group.getColor());
        syntax = syntax.replace("%short-rank%", group.getShortName());
        syntax = Formatter.format(FormatType.AND_TO_PARAGRAPH, syntax);
        event.setQuitMessage(syntax);
    }

}

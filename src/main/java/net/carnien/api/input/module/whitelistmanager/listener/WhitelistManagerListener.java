package net.carnien.api.input.module.whitelistmanager.listener;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienListener;
import net.carnien.api.input.module.WhitelistManager;
import net.carnien.api.util.FormatType;
import net.carnien.api.util.Formatter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class WhitelistManagerListener extends CarnienListener {

    public WhitelistManagerListener(Carnien carnien) {
        super(carnien);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        final FileConfiguration config = getCarnien().getConfig();
        final boolean isWhitelistEnabled = (boolean) config.get("whitelist.enabled");

        if (!isWhitelistEnabled) return;

        final WhitelistManager whitelistManager = (WhitelistManager) getCarnien().getModule("WhitelistManager");
        final Player player = event.getPlayer();
        final String playerName = player.getName();
        final UUID uuid = player.getUniqueId();
        final String ipAddress = player.getAddress().getAddress().getHostAddress();

        if (whitelistManager.isPlayerNameListed(playerName)) return;
        if (whitelistManager.isIpAddressListed(ipAddress)) return;
        if (whitelistManager.isUuidListed(uuid)) return;

        String prefix = config.getString("prefix");
        prefix = Formatter.format(FormatType.AND_TO_PARAGRAPH, prefix);
        player.kickPlayer(prefix + "§cYou are not whitelisted on this server!");
    }

}

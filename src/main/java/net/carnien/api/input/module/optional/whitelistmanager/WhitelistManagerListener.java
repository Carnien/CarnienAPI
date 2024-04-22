package net.carnien.api.input.module.optional.whitelistmanager;

import net.carnien.api.Carnien;
import net.carnien.api.input.module.essential.permissionmanager.PermissionManager;
import net.carnien.api.util.FormatType;
import net.carnien.api.util.Formatter;
import net.carnien.api.input.CarnienListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class WhitelistManagerListener extends CarnienListener {

    private final WhitelistManager whitelistManager;

    public WhitelistManagerListener(Carnien carnien, WhitelistManager whitelistManager) {
        super(carnien);
        this.whitelistManager = whitelistManager;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        final FileConfiguration config = getCarnien().getConfig();
        final boolean isWhitelistEnabled = (boolean) config.get("whitelist.enabled");

        if (!isWhitelistEnabled) return;

        final Player player = event.getPlayer();
        final PermissionManager permissionManager = getCarnien().getPermissionManager();

        if (permissionManager.hasPermission(player, "api.whitelist.bypass")) return;

        final String playerName = player.getName();

        if (whitelistManager.isPlayerNameListed(playerName)) return;

        final UUID uuid = player.getUniqueId();
        final String uuidStr = uuid.toString();

        if (whitelistManager.isUuidListed(uuidStr)) return;

        final String ipAddress = player.getAddress().getAddress().getHostAddress();

        if (whitelistManager.isIpAddressListed(ipAddress)) return;

        String prefix = config.getString("prefix");
        prefix = Formatter.format(FormatType.AND_TO_PARAGRAPH, prefix);
        player.kickPlayer(prefix + "§cYou are not whitelisted on this server!");
    }

}

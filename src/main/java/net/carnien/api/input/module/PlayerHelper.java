package net.carnien.api.input.module;

import net.carnien.api.Carnien;
import net.carnien.api.input.Module;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerHelper extends Module {

    public PlayerHelper(Carnien carnien) {
        super(carnien);
    }

    public UUID getUuidFromSavedPlayers(String playerName) {
        final OfflinePlayer offlinePlayer = getOfflinePlayer(playerName);

        if (offlinePlayer == null) return null;

        return offlinePlayer.getUniqueId();
    }

    public OfflinePlayer getOfflinePlayer(String name) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            final String searchedPlayerName = player.getName();

            if (searchedPlayerName.equalsIgnoreCase(name)) return player;
        }

        final OfflinePlayer[] offlinePlayers = Bukkit.getOfflinePlayers();

        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            final String offlinePlayerName = offlinePlayer.getName();

            if (offlinePlayerName == null) continue;

            if (offlinePlayerName.equalsIgnoreCase(name)) return offlinePlayer;
        }

        return null;
    }

}

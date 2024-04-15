package net.carnien.api.input.module.permissionmanager.listener;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienListener;
import net.carnien.api.input.module.PermissionManager;
import net.carnien.api.permission.Group;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PermissionManagerListener extends CarnienListener {

    public PermissionManagerListener(Carnien carnien) {
        super(carnien);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final String playerName = player.getName();
        final PermissionManager permissionManager = getCarnien().getPermissionManager();
        final Group group = permissionManager.getRank(player);
        final String groupName = group.getName();
        final Team team = permissionManager.getTeam(groupName);
        team.addEntry(playerName);
        permissionManager.updateScoreboard();
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final String playerName = player.getName();
        final PermissionManager permissionManager = getCarnien().getPermissionManager();
        final Group group = permissionManager.getRank(player);
        final String groupName = group.getName();
        final Team team = permissionManager.getTeam(groupName);
        team.removeEntry(playerName);
    }

}

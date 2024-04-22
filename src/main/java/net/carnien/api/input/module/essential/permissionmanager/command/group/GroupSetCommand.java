package net.carnien.api.input.module.essential.permissionmanager.command.group;

import net.carnien.api.Carnien;
import net.carnien.api.input.module.essential.permissionmanager.Group;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.module.essential.permissionmanager.PermissionManager;
import net.carnien.api.input.module.essential.carnienlogger.CarnienLogger;
import net.carnien.api.util.MojangApi;
import net.carnien.api.util.PlayerHelper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class GroupSetCommand extends CarnienCommand {

    public GroupSetCommand(Carnien carnien) {
        super(carnien, "set", "api.group.set", "group set <player-name> <group>");
        setArgs(2);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        final CarnienLogger logger = getCarnien().getCarnienLogger();
        final String playerName = args[0];
        final String groupName = args[1];
        final UUID uuid = PlayerHelper.getUuidFromSavedPlayers(playerName);

        if (uuid == null) {
            logger.sendMessage(sender, "No player named §f" + playerName +
                " §7found. Requesting mojang for uuid...");
            asyncTaskContinue(sender, playerName, groupName);
            return;
        }

        final PermissionManager permissionManager = getCarnien().getPermissionManager();
        final Group group = permissionManager.getGroup(groupName);

        if (group == null) {
            logger.sendError(sender, "Group named §4" + groupName + " §cdoes not exist.");
            return;
        }

        permissionManager.setRank(uuid, group);
        final OfflinePlayer player = PlayerHelper.getOfflinePlayer(playerName);
        logger.sendMessage(sender, "§aPlayer §e" + player.getName() + " §ahas been set to group " +
            group.getColor() + group.getName() + "§a.");
    }

    private void asyncTaskContinue(CommandSender sender, String playerName, String groupName) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getCarnien(), () -> {
            final UUID uuid = MojangApi.getUuid(playerName);
            final CarnienLogger logger = getCarnien().getCarnienLogger();

            if (uuid == null) {
                logger.sendError(sender, "Player §4" + playerName + " §cdoes not exist.");
                return;
            }

            final PermissionManager permissionManager = getCarnien().getPermissionManager();
            final Group group = permissionManager.getGroup(groupName);

            if (group == null) {
                logger.sendError(sender, "Group named §4" + groupName + " §cdoes not exist.");
                return;
            }

            permissionManager.setRank(uuid, group);
            final String realPlayerName = MojangApi.getName(playerName);
            logger.sendMessage(sender, "§aPlayer §e" + realPlayerName + " §ahas been set to group " +
                group.getColor() + group.getName() + "§a.");
        });
    }

}

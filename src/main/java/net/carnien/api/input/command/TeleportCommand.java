package net.carnien.api.input.command;

import net.carnien.api.Carnien;
import net.carnien.api.input.Command;
import net.carnien.api.input.module.CarnienLogger;
import net.carnien.api.input.module.PermissionManager;
import net.carnien.api.permission.Group;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportCommand extends Command {

    public TeleportCommand(Carnien carnien) {
        super(carnien, "Teleport", "api.teleport", "/teleport (player ...) <player/x, y, z>");
        setMinArgs(1);
        setAliases(new String[]{"tp"});
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        final CarnienLogger logger = getCarnien().getCarnienLogger();
        Location destination;
        int subtract = 1;

        try {
            final String zStr = args[args.length - 1];
            final String yStr = args[args.length - 2];
            final String xStr = args[args.length - 3];
            final int z = Integer.parseInt(zStr);
            final int y = Integer.parseInt(yStr);
            final int x = Integer.parseInt(xStr);
            final Player player = (Player) sender;
            final World world = player.getWorld();
            destination = new Location(world, x, y, z);
            subtract = 3;
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException exception) {
            final String destinationPlayerName = args[args.length - 1];
            final Player destinationPlayer = Bukkit.getPlayer(destinationPlayerName);

            if (destinationPlayer == null) {
                logger.sendError(sender, "Player §4" + destinationPlayerName + " §cis not online.");
                return;
            }

            destination = destinationPlayer.getLocation();
        }

        final List<Player> targetPlayers = new ArrayList<>();
        final int targetPlayersCount = args.length - subtract;

        if (targetPlayersCount > 0) {
            for (int i = 0; i < targetPlayersCount; i++) {
                final String playerName = args[i];
                final Player player = Bukkit.getPlayer(playerName);

                if (player == null) logger.sendError(sender, "Player §4" + playerName + " §cis not online.");
                else targetPlayers.add(player);
            }
        } else {
            final Player senderPlayer = (Player) sender;
            targetPlayers.add(senderPlayer);
        }

        for (Player player : targetPlayers) {
            player.teleport(destination);
            final PermissionManager permissionManager = getCarnien().getPermissionManager();
            final Group group = permissionManager.getRank(player);
            final int x = destination.getBlockX();
            final int y = destination.getBlockY();
            final int z = destination.getBlockZ();
            logger.sendMessage(sender, "§aPlayer " + group.getColor() + player.getName() +
                " §ahas been teleported to §e" + x + "§a, §e" + y + "§a, §e" + z + "§a.");
        }
    }

}

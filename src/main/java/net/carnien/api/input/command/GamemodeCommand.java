package net.carnien.api.input.command;

import net.carnien.api.Carnien;
import net.carnien.api.input.module.essential.permissionmanager.Group;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.module.essential.permissionmanager.PermissionManager;
import net.carnien.api.input.module.essential.carnienlogger.CarnienLogger;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GamemodeCommand extends CarnienCommand {

    public GamemodeCommand(Carnien carnien) {
        super(carnien, "gamemode", "api.gamemode",
            "gamemode <survival/creative/adventure/spectator> (player ...)");
        addAlias("gm");
        setMinArgs(1);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        final CarnienLogger logger = getCarnien().getCarnienLogger();
        final String gameModeStr = args[0];
        GameMode gameMode = null;

        try {
            final int gameModeId = Integer.parseInt(gameModeStr);
            gameMode = GameMode.getByValue(gameModeId);

            if (gameMode == null) {
                logger.sendError(sender, "Gamemode §4" + gameModeStr + " §cdoes not exist.");
                return;
            };
        } catch (NumberFormatException ignored) {
            try {
                final String gameModeStrUpper = gameModeStr.toUpperCase();
                gameMode = GameMode.valueOf(gameModeStrUpper);
            } catch (IllegalArgumentException ignored2) {
                logger.sendError(sender, "Gamemode §4" + gameModeStr + " §cdoes not exist.");
                return;
            }
        }

        final List<Player> players = new ArrayList<>();

        if (args.length == 1) {
            players.add((Player) sender);
        }

        for (int i = 1 ; i < args.length; i++) {
            final String playerName = args[i];
            final Player player = Bukkit.getPlayer(playerName);

            if (player == null) {
                logger.sendError(sender, "Player §4" + playerName + " §cis not online.");
                continue;
            }

            players.add(player);
        }

        final PermissionManager permissionManager = getCarnien().getPermissionManager();

        for (Player player : players) {
            final Group group  = permissionManager.getRank(player);
            player.setGameMode(gameMode);
            logger.sendMessage(sender, "§aGamemode for player " + group.getColor() + player.getName() +
                " §ahas been set to §e" + gameMode.name() + "§a.");
        }
    }

}

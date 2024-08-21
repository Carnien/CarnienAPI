package net.carnien.api.input.command.time;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.module.essential.carnienlogger.CarnienLogger;
import net.carnien.api.util.Time;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeSetCommand extends CarnienCommand {

    public TimeSetCommand(Carnien carnien) {
        super(carnien, "set", "api.time.set", "time set <day/night/1-24000>");
        setMinArgs(1);
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        final CarnienLogger logger = getCarnien().getCarnienLogger();
        final String timeStr = args[0];
        int tick;

        try {
            tick = Integer.parseInt(timeStr);

            if (tick < 1 || tick > 24000) {
                logger.sendError(sender, "Invalid tick range. Available: 1-24000");
                return;
            }
        } catch (NumberFormatException ignored) {
            try {
                final String timeStrUpper = timeStr.toUpperCase();
                final Time time = Time.valueOf(timeStrUpper);
                tick = time.getValue();
            } catch (IllegalArgumentException ignored2) {
                logger.sendError(sender, "Time §4" + timeStr + " §cdoes not exist.");
                return;
            }
        }

        final Player player = (Player) sender;
        final World world = player.getWorld();
        world.setTime(tick);
        logger.sendMessage(sender, "§aTime has been set to §e" + timeStr + "§a.");
    }

}

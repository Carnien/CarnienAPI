package net.carnien.api.input.module.essential.commandhandler;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.CarnienModule;
import net.carnien.api.input.module.essential.carnienlogger.CarnienLogger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class CommandHandler extends CarnienModule {

    private final Map<String, CarnienCommand> commands = new HashMap<>();

    public CommandHandler(Carnien carnien) {
        super(carnien, "CommandHandler");
        addListener(new CommandHandlerListener(carnien));
    }

    public void add(CarnienCommand command) {
        final String label = command.getLabel();

        if (!exists(label)) commands.put(label, command);

        final List<String> aliases = command.getAliases();

        for (String alias : aliases) {
            if (!exists(alias)) commands.put(alias, command);
        }
    }

    public void remove(String label) {
        if (exists(label)) commands.remove(label);
    }

    public boolean exists(String label) {
        return commands.containsKey(label);
    }

    public void handle(CommandSender sender, String label, String[] args) {
        final CarnienLogger logger = getCarnien().getCarnienLogger();

        if (!exists(label)) {
            logger.sendCommandNotExistError(sender, label);
            return;
        }

        args = replaceShortcuts(args);
        final CarnienCommand command = get(label);
        command.run(sender, args);
    }

    private String[] replaceShortcuts(String[] args) {
        final List<String> newArgs = new ArrayList<>();

        for (String arg : args) {
            if (arg.equals("@a")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    final String playerName = player.getName();
                    newArgs.add(playerName);
                }
            } else newArgs.add(arg);
        }

        return newArgs.toArray(new String[]{});
    }

    public CarnienCommand get(String label) {
        return commands.get(label);
    }

}

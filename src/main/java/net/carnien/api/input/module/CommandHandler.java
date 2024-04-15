package net.carnien.api.input.module;

import net.carnien.api.Carnien;
import net.carnien.api.input.Command;
import net.carnien.api.input.Module;
import net.carnien.api.input.module.commandHandler.listener.CommandHandlerListener;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler extends Module {

    private final Map<String, Command> commands = new HashMap<>();

    public CommandHandler(Carnien carnien) {
        super(carnien);
        addListener(new CommandHandlerListener(getCarnien()));
    }

    public void add(String label, Command command) {
        if (!exists(label)) commands.put(label, command);

        final String[] aliases = command.getAliases();

        if (aliases == null) return;

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

        final Command command = get(label);
        command.run(sender, args);
    }

    public Command get(String label) {
        return commands.get(label);
    }

}

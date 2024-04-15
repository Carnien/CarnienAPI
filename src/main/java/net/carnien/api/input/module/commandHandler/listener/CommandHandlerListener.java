package net.carnien.api.input.module.commandHandler.listener;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienListener;
import net.carnien.api.input.module.CommandHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;

public class CommandHandlerListener extends CarnienListener {

    public CommandHandlerListener(Carnien carnien) {
        super(carnien);
    }

    @EventHandler
    private void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent event) {
        event.setCancelled(true);
        final Player player = event.getPlayer();
        final String message = event.getMessage();
        final String[] splitMessage = message.split(" ");
        final String label = splitMessage[0].substring(1).trim().toLowerCase();

        if (label.isEmpty()) return;

        final String[] args = Arrays.copyOfRange(splitMessage, 1, splitMessage.length);
        final CommandHandler commandHandler = getCarnien().getCommandHandler();
        commandHandler.handle(player, label, args);
    }

}

package net.carnien.api.input;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import net.carnien.api.Carnien;
import net.carnien.api.input.module.CommandHandler;
import net.carnien.api.util.CarnienUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Module extends CarnienUtil {

    private final List<Listener> listeners = new ArrayList<>();
    private final List<PacketAdapter> adapters = new ArrayList<>();
    private final Map<String, Command> commands = new HashMap<>();
    private boolean enabled = false;

    public Module(Carnien carnien) {
        super(carnien);
    }

    public void enable() {
        enabled = true;
        registerListeners();
        registerAdapters();
        registerCommands();
        onEnable();
    }

    public void disable() {
        enabled = false;
        unregisterListeners();
        unregisterAdapters();
        unregisterCommands();
        onDisable();
    }

    protected void onEnable() { }

    protected void onDisable() { }

    protected void addListener(Listener listener) {
        if (!listeners.contains(listener)) listeners.add(listener);
    }

    protected void addAdapter(PacketAdapter adapter) {
        if (!adapters.contains(adapter)) adapters.add(adapter);
    }

    protected void addCommand(String label, Command command) {
        if (!commands.containsKey(label)) commands.put(label, command);
    }

    private void registerListeners() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        for (Listener listener : listeners) pluginManager.registerEvents(listener, getCarnien());
    }

    private void registerAdapters() {
        final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        for (PacketAdapter adapter : adapters) protocolManager.addPacketListener(adapter);
    }

    private void registerCommands() {
        final CommandHandler commandHandler = getCarnien().getCommandHandler();

        for (String commandStr : commands.keySet()) {
            final Command command = commands.get(commandStr);
            commandHandler.add(commandStr, command);
        }
    }

    private void unregisterListeners() {
        for (Listener listener : listeners) HandlerList.unregisterAll(listener);
    }

    private void unregisterAdapters() {
        final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        for (PacketAdapter adapter : adapters) protocolManager.removePacketListener(adapter);
    }

    private void unregisterCommands() {
        final CommandHandler commandHandler = getCarnien().getCommandHandler();
        for (String commandStr : commands.keySet()) commandHandler.remove(commandStr);
    }

    public boolean isEnabled() {
        return enabled;
    }

}

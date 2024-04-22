package net.carnien.api.input;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import net.carnien.api.Carnien;
import net.carnien.api.input.module.essential.commandhandler.CommandHandler;
import net.carnien.api.util.CarnienUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarnienModule extends CarnienUtil {

    private final String name;
    private boolean enabled = false;
    private final List<Listener> listeners = new ArrayList<>();
    private final List<PacketAdapter> adapters = new ArrayList<>();
    private final Map<String, CarnienCommand> commands = new HashMap<>();

    public CarnienModule(Carnien carnien, String name) {
        super(carnien);
        this.name = name;
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

    protected void addCommand(CarnienCommand command) {
        final String label = command.getLabel();
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
            final CarnienCommand command = commands.get(commandStr);
            commandHandler.add(command);
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

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

}

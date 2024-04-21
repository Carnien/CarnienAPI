package net.carnien.api.input.module;

import net.carnien.api.Carnien;
import net.carnien.api.input.Module;
import net.carnien.api.input.module.whitelistmanager.listener.WhitelistManagerListener;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.UUID;

public class WhitelistManager extends Module {

    private List<String> playerNames;
    private List<UUID> uuids;
    private List<String> ipAddresses;

    public WhitelistManager(Carnien carnien) {
        super(carnien);
        load();
        addListener(new WhitelistManagerListener(getCarnien()));
    }

    public boolean isPlayerNameListed(String name) {
        name= name.toLowerCase();
        return playerNames.contains(name);
    }

    public boolean isUuidListed(UUID uuid) {
        return uuids.contains(uuid);
    }

    public boolean isIpAddressListed(String ipAddress) {
        return ipAddresses.contains(ipAddress);
    }

    public void addPlayerName(String name) {
        name = name.toLowerCase();
        if (playerNames.contains(name)) return;
        playerNames.add(name);
        save();
    }

    public void addUuid(UUID uuid) {
        if (uuids.contains(uuid)) return;
        uuids.add(uuid);
        save();
    }

    public void addIpAddress(String ipAddress) {
        if (ipAddresses.contains(ipAddress)) return;
        ipAddresses.add(ipAddress);
        save();
    }

    public void load() {
        final FileConfiguration config = getCarnien().getConfig();
        playerNames = (List<String>) config.get("whitelist.player-names");
        uuids = (List<UUID>) config.get("whitelist.uuids");
        ipAddresses = (List<String>) config.get("whitelist.ip-addresses");
    }

    public void save() {
        final FileConfiguration config = getCarnien().getConfig();
        config.set("whitelist.player-names", playerNames);
        config.set("whitelist.uuids", uuids);
        config.set("whitelist.ip-addresses", ipAddresses);
        getCarnien().saveConfig();
    }

}

package net.carnien.api.input.module.optional.whitelistmanager;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienModule;
import net.carnien.api.input.module.optional.whitelistmanager.command.WhitelistCommand;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WhitelistManager extends CarnienModule {

    public WhitelistManager(Carnien carnien) {
        super(carnien, "WhitelistManager");
        addListener(new WhitelistManagerListener(carnien, this));
        addCommand(new WhitelistCommand(carnien, this));
    }

    public boolean isPlayerNameListed(String name) {
        name = name.toLowerCase();
        final List<String> playerNames = getPlayerNames();
        return playerNames.contains(name);
    }

    public boolean isUuidListed(UUID uuid) {
        final List<String> uuidStrs = getUuids();
        return uuidStrs.contains(uuid.toString());
    }

    public boolean isIpAddressListed(String ipAddress) {
        ipAddress = ipAddress.toLowerCase();
        final List<String> ipAddresses = getIpAddresses();
        return ipAddresses.contains(ipAddress);
    }

    public void addPlayerName(String name) {
        name = name.toLowerCase();
        if (isPlayerNameListed(name)) return;
        final List<String> playerNames = getPlayerNames();
        playerNames.add(name);
        final FileConfiguration config = getCarnien().getConfig();
        config.set("whitelist.player-names", playerNames);
        getCarnien().saveConfig();
    }

    public void addUuid(UUID uuid) {
        if (isUuidListed(uuid)) return;
        final List<String> uuids = getPlayerNames();
        uuids.add(uuid.toString());
        final FileConfiguration config = getCarnien().getConfig();
        config.set("whitelist.uuids", uuids);
        getCarnien().saveConfig();
    }

    public void addIpAddress(String ipAddress) {
        if (isIpAddressListed(ipAddress)) return;
        final List<String> ipAddresses = getIpAddresses();
        ipAddresses.add(ipAddress);
        final FileConfiguration config = getCarnien().getConfig();
        config.set("whitelist.ip-addresses", ipAddresses);
        getCarnien().saveConfig();
    }

    public List<String> getPlayerNames() {
        final FileConfiguration config = getCarnien().getConfig();
        return (List<String>) config.get("whitelist.player-names");
    }

    public List<String> getUuids() {
        final FileConfiguration config = getCarnien().getConfig();
        return (List<String>) config.get("whitelist.uuids");
    }

    public List<String> getIpAddresses() {
        final FileConfiguration config = getCarnien().getConfig();
        return (List<String>) config.get("whitelist.ip-addresses");
    }

}

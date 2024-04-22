package net.carnien.api.input.module.essential.permissionmanager;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienModule;
import net.carnien.api.input.module.essential.permissionmanager.command.GroupCommand;
import net.carnien.api.input.module.essential.permissionmanager.command.GroupsCommand;
import net.carnien.api.input.module.essential.permissionmanager.event.CarnienScoreboardUpdateEvent;
import net.carnien.api.util.CustomConfig;
import net.carnien.api.util.FormatType;
import net.carnien.api.util.Formatter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.util.*;

public class PermissionManager extends CarnienModule {

    private final File permissionFile;
    private final FileConfiguration permissionConfig;
    private Scoreboard scoreboardTemplate;

    public PermissionManager(Carnien carnien) {
        super(carnien, "PermissionManager");
        permissionFile = CustomConfig.create(carnien,"permissions.yml");
        permissionConfig = CustomConfig.get(permissionFile);
        saveDefaultPermissions();
        addListener(new PermissionManagerListener(carnien));
        addAdapter(new PermissionManagerAdapter(carnien));
        addCommand(new GroupCommand(carnien));
        addCommand(new GroupsCommand(carnien));
        initializeScoreboardTemplate();
    }

    public void addPermission(Group group, String permission) {
        final String groupName = group.getName();
        final String groupNameLower = groupName.toLowerCase();

        if (!groupExists(groupNameLower)) return;

        final List<String> permissionsList = permissionConfig.getStringList("groups." + groupNameLower + ".permissions");
        final String permissionsLower = permission.toLowerCase();

        if (permissionsList.contains(permissionsLower)) return;

        permissionsList.add(permission);
        permissionConfig.set("groups." + groupNameLower + ".permissions", permissionsList);
        CustomConfig.save(permissionFile, permissionConfig);
    }

    public void removePermission(Group group, String permission) {
        final String groupName = group.getName();
        final String groupNameLower = groupName.toLowerCase();

        if (!groupExists(groupNameLower)) return;

        final List<String> permissionsList = permissionConfig.getStringList("groups." + groupNameLower + ".permissions");
        final String permissionLower = permission.toLowerCase();

        if (!permissionsList.contains(permissionLower)) return;

        permissionsList.remove(permission);
        permissionConfig.set("groups." + groupNameLower + ".permissions", permissionsList);
        CustomConfig.save(permissionFile, permissionConfig);
    }

    public boolean hasPermission(Player player, String permission) {
        permission = permission.toLowerCase();
        final Group group = getRank(player);
        final List<String> permissions = group.getPermissions();
        return permissions.contains(permission);
    }

    public void updateScoreboard() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        final Map<Player, Scoreboard> scoreboards = new HashMap<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            final Scoreboard scoreboard = getScoreboardForPlayer(player);
            scoreboards.put(player, scoreboard);
        }

        final CarnienScoreboardUpdateEvent event = new CarnienScoreboardUpdateEvent(scoreboards);
        pluginManager.callEvent(event);
        final boolean cancelled = event.isCancelled();

        if (cancelled) return;

        for (Player player : scoreboards.keySet()) {
            final Scoreboard scoreboard = scoreboards.get(player);
            player.setScoreboard(scoreboard);
        }
    }

    private Scoreboard getScoreboardForPlayer(Player player) {
        return copyGlobalScoreboard();
    }

    private Scoreboard copyGlobalScoreboard() {
        final ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        final Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        final Set<Team> teams = scoreboardTemplate.getTeams();

        for (Team team : teams) {
            final Team newTeam = scoreboard.registerNewTeam(team.getName());
            newTeam.setPrefix(team.getPrefix());
            newTeam.setSuffix(team.getSuffix());
            newTeam.setColor(team.getColor());
            final Set<String> entries = team.getEntries();

            for (String entry : entries) newTeam.addEntry(entry);
        }

        return scoreboard;
    }

    public void createGroup(String groupName) {
        if (groupExists(groupName)) return;

        final String groupNameLower = groupName.toLowerCase();
        permissionConfig.set("groups." + groupNameLower + ".name", groupName);
        permissionConfig.set("groups." + groupNameLower + ".short-name", groupName);
        permissionConfig.set("groups." + groupNameLower + ".color", "&f");
        permissionConfig.set("groups." + groupNameLower + ".message-color", "&7");
        permissionConfig.set("groups." + groupNameLower + ".permissions", new ArrayList<>());
        CustomConfig.save(permissionFile, permissionConfig);
        final Group group = getGroup(groupName);
        final int index = getTeamIndex(groupName);
        final Team team = scoreboardTemplate.registerNewTeam(index + groupName);
        final FileConfiguration config = getCarnien().getConfig();
        final String prefix = (String) config.get("name-tag.prefix");
        String teamPrefix = prefix.replace("%rank%", group.getName());
        teamPrefix = teamPrefix.replace("%short-rank%", group.getShortName());
        teamPrefix = teamPrefix.replace("%rank-color%", group.getColor());
        teamPrefix = Formatter.format(FormatType.AND_TO_PARAGRAPH, teamPrefix);
        final String groupColor = group.getColor();
        final char colorNumber = groupColor.charAt(1);
        final String color = String.valueOf(colorNumber);
        final ChatColor chatColor = ChatColor.getByChar(color);
        team.setColor(chatColor);
        team.setPrefix(teamPrefix);
    }

    public void deleteGroup(String groupName) {
        if (!groupExists(groupName)) return;

        final Group group = getGroup(groupName);
        final String groupNameLower = groupName.toLowerCase();
        final String groupNameOriginal = group.getName();
        final Team team = scoreboardTemplate.getTeam(groupNameOriginal);
        team.unregister();
        final ConfigurationSection section = permissionConfig.getConfigurationSection("ranks");

        for (String uuidStr : section.getKeys(false)) {
            final String rank = (String) section.get(uuidStr);

            if (!rank.equals(groupNameLower)) continue;

            final UUID uuid = UUID.fromString(uuidStr);
            final Group defaultGroup = getDefaultGroup();
            setRank(uuid, defaultGroup);
        }

        updateScoreboard();
        permissionConfig.set("groups." + groupNameLower, null);
        CustomConfig.save(permissionFile, permissionConfig);
    }

    public boolean groupExists(String groupName) {
        final String groupNameLower = groupName.toLowerCase();
        return permissionConfig.get("groups." + groupNameLower) != null;
    }

    public Group getGroup(String groupName) {
        final String groupNameLower = groupName.toLowerCase();
        final ConfigurationSection section = permissionConfig.getConfigurationSection("groups." + groupNameLower);

        if (section == null) return null;

        final Group group = new Group();
        final String name = permissionConfig.getString("groups." + groupNameLower + ".name");
        group.setName(name);
        final String shortName = permissionConfig.getString("groups." + groupNameLower + ".short-name");
        group.setShortName(shortName);
        final String color = permissionConfig.getString("groups." + groupNameLower + ".color");
        group.setColor(color);
        final String messageColor = permissionConfig.getString("groups." + groupNameLower + ".message-color");
        group.setMessageColor(messageColor);
        final List<String> permissions = permissionConfig.getStringList("groups." + groupNameLower + ".permissions");
        group.setPermissions(permissions);
        return group;
    }

    public Group getRank(Player player) {
        final ConfigurationSection section = permissionConfig.getConfigurationSection("ranks");

        if (section == null) return getDefaultGroup();

        final UUID uuid = player.getUniqueId();
        final String groupName = permissionConfig.getString("ranks." + uuid);

        if (groupName == null) return getDefaultGroup();

        return getGroup(groupName);
    }

    public void setRank(UUID uuid, Group group) {
        final String groupName = group.getName();
        final String groupNameLower = groupName.toLowerCase();
        permissionConfig.set("ranks." + uuid, groupNameLower);
        CustomConfig.save(permissionFile, permissionConfig);
        final Player player = Bukkit.getPlayer(uuid);

        if (player == null) return;

        final String playerName = player.getName();

        for (Team team : scoreboardTemplate.getTeams()) {
            final Set<String> entries = team.getEntries();

            if (entries.contains(playerName)) team.removeEntry(playerName);
        }

        final Team team = getTeam(groupName);
        team.addEntry(playerName);
        updateScoreboard();
    }

    public Group getDefaultGroup() {
        final String defaultGroupName = permissionConfig.getString("default-group");
        if (defaultGroupName == null) return null;
        return getGroup(defaultGroupName);
    }

    public List<Group> getGroups() {
        final ConfigurationSection section = permissionConfig.getConfigurationSection("groups");
        final List<Group> groupList = new ArrayList<>();

        for (String groupName : section.getKeys(false)) {
            final Group group = getGroup(groupName);
            groupList.add(group);
        }

        return groupList;
    }

    public Team getTeam(String teamName) {
        final int index = getTeamIndex(teamName);
        final Set<Team> teamSet = scoreboardTemplate.getTeams();
        final List<Team> teams = new ArrayList<>(teamSet);
        teams.sort(Comparator.comparing(Team::getName));
        return teams.get(index);
    }

    private int getTeamIndex(String teamName) {
        teamName = teamName.toLowerCase();
        final List<Group> groups = getGroups();

        for (int i = 0; i < groups.size(); i++) {
            final Group group = groups.get(i);
            final String groupName = group.getName().toLowerCase();
            if (groupName.equals(teamName)) return i;
        }

        return 0;
    }

    private void saveDefaultPermissions() {
        ConfigurationSection configurationSection = permissionConfig.getConfigurationSection("ranks");

        if (configurationSection == null) permissionConfig.set("ranks", new HashMap<>());

        final String defaultGroup = permissionConfig.getString("default-group");

        if (defaultGroup == null) {
            permissionConfig.set("default-group", "default");

            configurationSection = permissionConfig.getConfigurationSection("groups.default");

            if (configurationSection == null) {
                permissionConfig.set("groups.default.name", "Default");
                permissionConfig.set("groups.default.short-name", "Default");
                permissionConfig.set("groups.default.color", "&f");
                permissionConfig.set("groups.default.message-color", "&7");
                permissionConfig.set("groups.default.permissions", new ArrayList<>());
            }
        }

        CustomConfig.save(permissionFile, permissionConfig);
    }

    private void initializeScoreboardTemplate() {
        final FileConfiguration config = getCarnien().getConfig();
        final String prefix = (String) config.get("name-tag.prefix");
        final ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        scoreboardTemplate = scoreboardManager.getNewScoreboard();
        final List<Group> groups = getGroups();

        for (int i = 0; i < groups.size(); i++) {
            final Group group = groups.get(i);
            final String groupName = group.getName();
            final Team team = scoreboardTemplate.registerNewTeam(i + groupName);
            String teamPrefix = prefix.replace("%rank%", group.getName());
            teamPrefix = teamPrefix.replace("%short-rank%", group.getShortName());
            teamPrefix = teamPrefix.replace("%rank-color%", group.getColor());
            teamPrefix = Formatter.format(FormatType.AND_TO_PARAGRAPH, teamPrefix);
            final String groupColor = group.getColor();
            final char colorNumber = groupColor.charAt(1);
            final String color = String.valueOf(colorNumber);
            final ChatColor chatColor = ChatColor.getByChar(color);
            team.setColor(chatColor);
            team.setPrefix(teamPrefix);
        }
    }

}

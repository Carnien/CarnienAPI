package net.carnien.api.input;

import net.carnien.api.Carnien;
import net.carnien.api.input.module.essential.carnienlogger.CarnienLogger;
import net.carnien.api.input.module.essential.permissionmanager.PermissionManager;
import net.carnien.api.util.CarnienUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.*;

public class CarnienCommand extends CarnienUtil {

    private final String label;
    private List<String> aliases = new ArrayList<>();
    private final String permission;
    private final String usage;
    private int minArgs = 0;
    private int maxArgs = Integer.MAX_VALUE;
    private final Map<String, CarnienCommand> subCommands = new HashMap<>();

    public CarnienCommand(Carnien carnien, String label, String permission, String usage) {
        super(carnien);
        this.label = label.toLowerCase();
        this.permission = permission.toLowerCase();
        this.usage = usage.toLowerCase();
    }

    public void run(CommandSender sender, String[] args) {
        final PermissionManager permissionManager = getCarnien().getPermissionManager();
        final CarnienLogger logger = getCarnien().getCarnienLogger();

        if (permission != null) {
            final Player player = (Player) sender;

            if (!permission.isEmpty() && !permissionManager.hasPermission(player, permission)) {
                logger.sendPermissionDeniedError(sender);
                return;
            }
        }

        if (subCommands.isEmpty()) {
            if (args.length < minArgs || args.length > maxArgs) {
                logger.sendUsageError(sender, getUsage());
                return;
            }

            onCommand(sender, args);
            return;
        }

        if (args.length == 0) {
            logger.sendUsageError(sender, getUsage());
            return;
        }

        final String subCommandStr = args[0];
        final CarnienCommand subCommand = subCommands.get(subCommandStr);

        if (subCommand == null) {
            logger.sendCommandNotExistError(sender, subCommandStr);
            return;
        }

        final String[] subCommandArgs = Arrays.copyOfRange(args, 1, args.length);
        subCommand.run(sender, subCommandArgs);
    }

    protected void onCommand(CommandSender sender, String[] args) { }

    protected void addSubCommand(CarnienCommand subCommand) {
        final String label = subCommand.getLabel();
        if (!subCommands.containsKey(label)) subCommands.put(label, subCommand);
    }

    public String getLabel() {
        return label;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void addAlias(String alias) {
        alias = alias.toLowerCase();
        if (!aliases.contains(alias)) aliases.add(alias);
    }

    public void removeAlias(String alias) {
        alias = alias.toLowerCase();
        aliases.remove(alias);
    }

    public String getPermission() {
        return permission;
    }

    public String getUsage() {
        if (subCommands.keySet().isEmpty()) return usage;

        final String[] subCommandsArr = this.subCommands.keySet().toArray(new String[]{});
        final StringBuilder subCommandsStr = new StringBuilder();
        final String fistSubCommandStr = subCommandsArr[0];
        subCommandsStr.append(fistSubCommandStr);

        for (int i = 1; i < subCommandsArr.length; i++) {
            subCommandsStr.append("/");
            subCommandsStr.append(subCommandsArr[i]);
        }

        return usage + " <" + subCommandsStr + ">";
    }

    public void setArgs(int args) {
        minArgs = args;
        maxArgs = args;
    }

    public int getMinArgs() {
        return minArgs;
    }

    public void setMinArgs(int minArgs) {
        this.minArgs = minArgs;
    }

    public int getMaxArgs() {
        return maxArgs;
    }

    public void setMaxArgs(int maxArgs) {
        this.maxArgs = maxArgs;
    }

}

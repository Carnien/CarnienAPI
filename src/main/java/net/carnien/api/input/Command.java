package net.carnien.api.input;

import net.carnien.api.Carnien;
import net.carnien.api.input.module.PermissionManager;
import net.carnien.api.input.module.CarnienLogger;
import net.carnien.api.util.CarnienUtil;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Command extends CarnienUtil {

    private final String name;
    private String[] aliases;
    private final String permission;
    private final String usage;
    private final Map<String, Command> subCommands = new HashMap<>();
    private int minArgs = 0;
    private int maxArgs = 127;
    private ItemStack itemStack = new ItemStack(Material.GRASS_BLOCK);
    private String[] description;

    public Command(Carnien carnien, String name, String permission, String usage) {
        super(carnien);
        this.name = name;
        this.permission = permission;
        this.usage = usage;
    }

    public void run(CommandSender sender, String[] args) {
        final PermissionManager permissionManager = getCarnien().getPermissionManager();
        final Player player = (Player) sender;
        final CarnienLogger logger = getCarnien().getCarnienLogger();

        if (!permissionManager.hasPermission(player, permission)) {
            logger.sendPermissionDeniedError(sender);
            return;
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
        final Command subCommand = subCommands.get(subCommandStr);

        if (subCommand == null) {
            logger.sendCommandNotExistError(sender, subCommandStr);
            return;
        }

        final String[] subCommandArgs = Arrays.copyOfRange(args, 1, args.length);
        subCommand.run(sender, subCommandArgs);
    }

    protected void onCommand(CommandSender sender, String[] args) { }

    protected void addSubCommand(String label, Command subCommand) {
        if (subCommands.containsKey(label)) return;
        subCommands.put(label, subCommand);
    }

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
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

    public ItemStack getItemStack() {
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (description != null) {
            final List<String> lore = new ArrayList<>();

            for (String line : description) {
                final String newLine = "§e" + line;
                lore.add(newLine);
            }

            itemMeta.setLore(lore);
        }

        itemMeta.setDisplayName("§a" + name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
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

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public String[] getDescription() {
        return description;
    }

    public void setDescription(String[] description) {
        this.description = description;
    }

}

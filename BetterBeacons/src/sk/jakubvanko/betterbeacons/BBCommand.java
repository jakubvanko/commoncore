package sk.jakubvanko.betterbeacons;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import sk.jakubvanko.commoncore.ConfigData;
import sk.jakubvanko.commoncore.MessageManager;

public class BBCommand implements CommandExecutor {

    private ConfigData configData;
    private MessageManager messageManager;

    public BBCommand(ConfigData configData) {
        this.configData = configData;
        this.messageManager = new MessageManager(configData.getMessageMap());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            messageManager.sendMessage("default_command", sender, null);
        } else {
            switch (args[0].toLowerCase()) {
                case "tool": {
                    if (!sender.hasPermission("betterbeacons.givetool")) {
                        messageManager.sendMessage("no_permission", sender, null);
                    } else {
                        ItemStack tool = configData.getItemStackMap().get("TOOL");
                        if (args.length > 1) {
                            Player targetedPlayer = Bukkit.getPlayer(args[1]);
                            if (targetedPlayer == null) {
                                messageManager.sendMessage("player_not_found", sender, null);
                            } else {
                                targetedPlayer.getInventory().addItem(tool.clone());
                            }
                        } else {
                            if (sender instanceof Player) {
                                Player targetedPlayer = (Player) sender;
                                targetedPlayer.getInventory().addItem(tool.clone());
                            } else {
                                messageManager.sendMessage("player_not_found", sender, null);
                            }
                        }
                    }
                }
                break;
            }
        }
        return true;
    }
}

package sk.jakubvanko.ultrachest;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import sk.jakubvanko.commoncore.CCMaterial;
import sk.jakubvanko.commoncore.ConfigManager;
import sk.jakubvanko.commoncore.MessageManager;

import java.util.HashMap;
import java.util.Map;

public class UCCommand implements CommandExecutor {

    private ConfigManager configManager;
    private UCConfigData configData;
    private MessageManager messageManager;
    private RewardGenerator rewardGenerator;

    public UCCommand(ConfigManager configManager, UCConfigData configData) {
        this.configManager = configManager;
        this.configData = configData;
        this.messageManager = new MessageManager(configData.getMessageMap());
        this.rewardGenerator = new RewardGenerator(configData);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            messageManager.sendMessage("default_command", sender, null);
        } else {
            switch (args[0].toLowerCase()) {
                case "give":
                case "get": {
                    if (!sender.hasPermission("ultrachest.give")) {
                        messageManager.sendMessage("no_permission", sender, null);
                        return true;
                    }
                    if (args.length < 3) {
                        messageManager.sendMessage("wrong_args", sender, null);
                        return true;
                    }
                    Player targetPlayer = Bukkit.getPlayer(args[1]);
                    if (targetPlayer == null) {
                        messageManager.sendMessage("player_not_found", sender, null);
                        return true;
                    }
                    int amount = 1;
                    if (args.length >= 4) {
                        Integer parsedAmount = tryParse(args[3]);
                        if (parsedAmount != null) {
                            amount = parsedAmount;
                        }
                    }
                    switch (args[2].toLowerCase()) {
                        case "all": {
                            for (ItemStack chest : configData.getChestSettingsMap().keySet()) {
                                ItemStack chestToGive = new ItemStack(chest);
                                chestToGive.setAmount(amount);
                                targetPlayer.getInventory().addItem(chestToGive);
                            }
                            sendChestGivenMessage(sender, targetPlayer, amount*configData.getChestSettingsMap().keySet().size());
                            return true;
                        }
                        case "random": {
                            Map<ItemStack, Integer> chances = new HashMap<>();
                            // Creating chances map
                            if (args.length < 5) {
                                for (ItemStack chest : configData.getChestSettingsMap().keySet()) {
                                    chances.put(chest, 1);
                                }
                            } else {
                                String additionalArgs = args[4];
                                additionalArgs = additionalArgs.replaceAll(" ", "");
                                String[] chestDataArray = additionalArgs.split(",");
                                for (String chestData : chestDataArray) {
                                    String[] splitChestData = chestData.split("-");
                                    int chance = 1;
                                    if (splitChestData.length > 1) {
                                        Integer parsedChance = tryParse(splitChestData[1]);
                                        if (parsedChance != null) {
                                            chance = parsedChance;
                                        }
                                    }
                                    ItemStack chest = configData.getItemStackMap().get(splitChestData[0]);
                                    if (chest == null) {
                                        Map<String, String> abbreviations = new HashMap<>();
                                        abbreviations.put("%chestname", splitChestData[0]);
                                        messageManager.sendMessage("chest_not_found", sender, abbreviations);
                                        return true;
                                    }
                                    chances.put(chest, chance);
                                }
                            }
                            // Giving generated chests to the specified player
                            for (int i = 0; i < amount; i++) {
                                ItemStack generatedChest = rewardGenerator.generateItemFromMap(chances);
                                targetPlayer.getInventory().addItem(generatedChest);
                            }
                            sendChestGivenMessage(sender, targetPlayer, amount);
                            return true;
                        }
                        default: {
                            ItemStack chest = configData.getItemStackMap().get(args[2]);
                            if (chest == null) {
                                Map<String, String> abbreviations = new HashMap<>();
                                abbreviations.put("%chestname", args[2]);
                                messageManager.sendMessage("chest_not_found", sender, abbreviations);
                                return true;
                            }
                            ItemStack generatedChest = new ItemStack(chest);
                            generatedChest.setAmount(amount);
                            targetPlayer.getInventory().addItem(generatedChest);
                            sendChestGivenMessage(sender, targetPlayer, amount);
                            return true;
                        }
                    }
                }
                case "chest":
                case "chests": {
                    if (!sender.hasPermission("ultrachest.list")) {
                        messageManager.sendMessage("no_permission", sender, null);
                        return true;
                    }
                    String allChests = "";
                    boolean first = true;
                    for (String chestName : configData.getChestIdentifiers()) {
                        if (!first) {
                            allChests += ", ";
                        } else {
                            first = false;
                        }
                        allChests += chestName;
                    }
                    Map<String, String> abbreviations = new HashMap<>();
                    abbreviations.put("%allchests", allChests);
                    messageManager.sendMessage("chest_listing", sender, abbreviations);
                    return true;
                }
                case "register": {
                    if (!sender.hasPermission("ultrachest.register")) {
                        messageManager.sendMessage("no_permission", sender, null);
                        return true;
                    }
                    if (!(sender instanceof Player)) {
                        messageManager.sendMessage("not_player", sender, null);
                        return true;
                    }
                    Player player = (Player) sender;
                    ItemStack playerItemStack;
                    if (CCMaterial.isNewVersion()){
                        playerItemStack = player.getInventory().getItemInMainHand();
                    }
                    else {
                        playerItemStack = player.getItemInHand();
                    }
                    if (playerItemStack == null) return true;
                    if (playerItemStack.getType() == CCMaterial.AIR.parseMaterial()) return true;
                    ItemStack itemToRegister = new ItemStack(playerItemStack);
                    itemToRegister.setAmount(1);
                    String itemIdentifier = configManager.registerItem(itemToRegister);
                    Map<String, String> abbreviations = new HashMap<>();
                    abbreviations.put("%itemidentifier", itemIdentifier);
                    messageManager.sendMessage("item_registered", sender, abbreviations);
                    return true;
                }
                default: {
                    messageManager.sendMessage("default_command", sender, null);
                }
            }
        }
        return true;
    }

    private void sendChestGivenMessage(CommandSender sender, Player targetPlayer, int chestAmount) {
        Map<String, String> abbreviations = new HashMap<>();
        abbreviations.put("%chestamount", chestAmount + "");
        abbreviations.put("%playername", targetPlayer.getDisplayName());
        messageManager.sendMessage("chest_given", sender, abbreviations);
    }

    private Integer tryParse(String value) {
        try {
            int i = Integer.parseInt(value);
            return i;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

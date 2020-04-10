package sk.jakubvanko.betterbeacons;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import sk.jakubvanko.betterbeacons.actions.SetBeaconEffect;
import sk.jakubvanko.commoncore.ClickAction;
import sk.jakubvanko.commoncore.ConfigManager;
import sk.jakubvanko.commoncore.IClickActionFactory;
import sk.jakubvanko.commoncore.InventoryData;
import sk.jakubvanko.commoncore.actions.CloseInventory;
import sk.jakubvanko.commoncore.actions.OpenInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BBConfigManager extends ConfigManager {

    public BBConfigManager(String configPath, IClickActionFactory clickActionFactory, Plugin plugin) {
        super(configPath, clickActionFactory, plugin);
    }

    @Override
    protected Map<String, InventoryData> loadInventories(Map<String, ItemStack> itemStackMap) {
        Map<String, InventoryData> inventoryMap = super.loadInventories(itemStackMap);

        // Adding setup effect menu actions and arguments
        for (Map.Entry<Integer, List<ClickAction>> entry : inventoryMap.get("setup_menu").getSlotActionMap().entrySet()) {
            for (ClickAction action : entry.getValue()) {
                if (action instanceof SetBeaconEffect) {
                    action.addArgument("PRIMARY", true);
                    action.addArgument("SECONDARY", false);
                    break;
                }
            }
            Map<String, Object> arguments = new HashMap<>();
            arguments.put("INVENTORY_NAME", "secondary_menu");
            entry.getValue().add(new OpenInventory(arguments));
        }

        // Adding primary effect menu actions and arguments
        for (Map.Entry<Integer, List<ClickAction>> entry : inventoryMap.get("primary_menu").getSlotActionMap().entrySet()) {
            for (ClickAction action : entry.getValue()) {
                if (action instanceof SetBeaconEffect) {
                    action.addArgument("PRIMARY", true);
                    action.addArgument("SECONDARY", false);
                    break;
                }
            }
            Map<String, Object> arguments = new HashMap<>();
            entry.getValue().add(new CloseInventory(arguments));
        }

        // Adding secondary effect menu actions and arguments
        for (Map.Entry<Integer, List<ClickAction>> entry : inventoryMap.get("secondary_menu").getSlotActionMap().entrySet()) {
            for (ClickAction action : entry.getValue()) {
                if (action instanceof SetBeaconEffect) {
                    action.addArgument("PRIMARY", false);
                    action.addArgument("SECONDARY", true);
                    break;
                }
            }
            Map<String, Object> arguments = new HashMap<>();
            entry.getValue().add(new CloseInventory(arguments));
        }

        // Formatting lore abbreviations (price, money price, needed tier)
        inventoryMap = formatLoreAbbreviations(itemStackMap, inventoryMap);
        return inventoryMap;
    }

    private Map<String, InventoryData> formatLoreAbbreviations(Map<String, ItemStack> itemStackMap, Map<String, InventoryData> inventoryMap) {
        for (Map.Entry<String, InventoryData> stringToDataEntry : inventoryMap.entrySet()) {
            InventoryData inventoryData = stringToDataEntry.getValue();
            Inventory inventory = inventoryData.getInventory();
            for (Map.Entry<Integer, List<ClickAction>> slotToActionEntry : inventoryData.getSlotActionMap().entrySet()) {
                Integer slotIndex = slotToActionEntry.getKey();
                List<String> loreToAdd = new ArrayList<>();
                Map<String, String> abbreviations = new HashMap<>();
                for (ClickAction clickAction : slotToActionEntry.getValue()) {
                    if (clickAction instanceof SetBeaconEffect) {
                        String moneyPriceMessage = clickAction.getArgumentString("MONEY_PRICE_MESSAGE", null);
                        if (moneyPriceMessage != null) {
                            loreToAdd.add(moneyPriceMessage);
                        }
                        Integer neededTier = clickAction.getArgumentInt("TIER", 1);
                        abbreviations.put("%nt", neededTier.toString());
                        List<String> priceList = clickAction.getArgumentList("ITEM_PRICE", new ArrayList<>());
                        for (String priceIdentifier : priceList) {
                            ItemStack price = itemStackMap.get(priceIdentifier);
                            String priceName = price.getItemMeta().getDisplayName();
                            loreToAdd.add(priceName);
                        }
                    }
                }
                ItemStack icon = inventory.getItem(slotIndex);
                ItemMeta iconMeta = icon.getItemMeta();
                List<String> oldLore = iconMeta.getLore();
                int priceAbbreviationIndex = -1;
                for (int i = 0; i < oldLore.size(); i++) {
                    if (oldLore.get(i).contains("%price")) {
                        priceAbbreviationIndex = i;
                        break;
                    }
                }
                if (priceAbbreviationIndex > -1) {
                    oldLore.remove(priceAbbreviationIndex);
                    oldLore.addAll(priceAbbreviationIndex, loreToAdd);
                }
                iconMeta.setLore(oldLore);
                icon.setItemMeta(iconMeta);
                formatLoreAbbreviations(icon, abbreviations);
            }
        }
        return inventoryMap;
    }
}

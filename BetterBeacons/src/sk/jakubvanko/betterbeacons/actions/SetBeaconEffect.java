package sk.jakubvanko.betterbeacons.actions;

import org.bukkit.Effect;
import org.bukkit.block.Beacon;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import sk.jakubvanko.betterbeacons.BBEventArguments;
import sk.jakubvanko.betterbeacons.EffectTimeManager;
import sk.jakubvanko.commoncore.ClickAction;
import sk.jakubvanko.commoncore.ConfigData;
import sk.jakubvanko.commoncore.MessageManager;
import sk.jakubvanko.commoncore.VaultManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetBeaconEffect extends ClickAction<BBEventArguments> {

    public SetBeaconEffect(Map<String, Object> arguments) {
        super(arguments);
    }

    @Override
    public void onSlotClicked(BBEventArguments eventArguments) {
        Beacon beacon = eventArguments.getBeacon();
        MessageManager messageManager = eventArguments.getMessageManager();
        VaultManager vaultManager = eventArguments.getVaultManager();
        Boolean primary = getArgumentBool("PRIMARY", false);
        Boolean secondary = getArgumentBool("SECONDARY", false);
        String effectTypeName = getArgumentString("EFFECT", "SPEED");
        Integer neededTier = getArgumentInt("TIER", 1);
        List<String> itemPrices = getArgumentList("ITEM_PRICE", new ArrayList<>());
        Integer moneyPrice = getArgumentInt("MONEY_PRICE", 0);
        PotionEffectType potionEffectType = PotionEffectType.getByName(effectTypeName);
        if (potionEffectType == null) {
            messageManager.logMessage("ERROR: PotionEffectType " + effectTypeName + " not found!");
            return;
        }
        Player player = eventArguments.getPlayer();
        if (beacon.getTier() < neededTier) {
            Map<String, String> abbreviations = new HashMap<>();
            abbreviations.put("%nt", neededTier.toString());
            abbreviations.put("%bt", beacon.getTier() + "");
            messageManager.sendMessage("low_tier", player, abbreviations);
            return;
        }
        ConfigData configData = eventArguments.getConfigData();
        if (!checkForItems(player.getInventory(), itemPrices, configData)) {
            messageManager.sendMessage("not_enough_items", player, null);
            return;
        }
        if (!vaultManager.withdrawMoney(player, moneyPrice)) {
            messageManager.sendMessage("not_enough_money", player, null);
            return;
        }
        removeItems(player, itemPrices, configData);
        if (primary) beacon.setPrimaryEffect(potionEffectType);
        if (secondary) {
            beacon.setSecondaryEffect(potionEffectType);
            if (beacon.getTier() != 4) messageManager.sendMessage("secondary_set_low_tier", player, null);
            if (beacon.getPrimaryEffect() == null) messageManager.sendMessage("secondary_set_no_primary", player, null);
        }
        beacon.update();
        player.playEffect(beacon.getLocation(), Effect.DRAGON_BREATH, null);
        EffectTimeManager effectTimeManager = eventArguments.getEffectTimeManager();
        effectTimeManager.addEffectDuration(beacon, potionEffectType, primary, secondary);
    }

    private void removeItems(Player player, List<String> itemIdentifiers, ConfigData configData) {
        if (itemIdentifiers == null) return;
        for (String itemIdentifier : itemIdentifiers) {
            ItemStack item = configData.getItemStackMap().get(itemIdentifier);
            ItemStack itemToRemove = new ItemStack(item.getType(), item.getAmount());
            player.getInventory().removeItem(itemToRemove);
        }
        player.updateInventory();
    }

    private boolean checkForItems(Inventory inventory, List<String> itemIdentifiers, ConfigData configData) {
        if (itemIdentifiers == null) return true;
        for (String itemIdentifier : itemIdentifiers) {
            ItemStack item = configData.getItemStackMap().get(itemIdentifier);
            if (!(inventory.contains(item.getType(), item.getAmount()))) {
                return false;
            }
        }
        return true;
    }
}

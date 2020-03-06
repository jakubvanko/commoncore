package sk.jakubvanko.ultrachest;

import org.bukkit.inventory.ItemStack;
import sk.jakubvanko.commoncore.ConfigData;

import java.util.List;
import java.util.Map;

public class UCConfigData extends ConfigData {

    private Map<ItemStack, Map<ItemStack, Integer>> chestSettingsMap;
    private List<String> chestIdentifiers;
    private Map<ItemStack, List<RewardData>> rewardSettingsMap;

    public Map<ItemStack, Map<ItemStack, Integer>> getChestSettingsMap() {
        return chestSettingsMap;
    }

    public List<String> getChestIdentifiers() {
        return chestIdentifiers;
    }

    public Map<ItemStack, List<RewardData>> getRewardSettingsMap() {
        return rewardSettingsMap;
    }

    public UCConfigData(ConfigData cd, Map<ItemStack, Map<ItemStack, Integer>> chestSettingsMap, List<String> chestIdentifiers, Map<ItemStack, List<RewardData>> rewardSettingsMap) {
        super(cd.getConfig(), cd.getItemStackMap(), cd.getRecipeMap(), cd.getInventoryDataMap(), cd.getMessageMap());
        this.chestSettingsMap = chestSettingsMap;
        this.chestIdentifiers = chestIdentifiers;
        this.rewardSettingsMap = rewardSettingsMap;
    }
}

package sk.jakubvanko.ultrachest;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import sk.jakubvanko.commoncore.ConfigData;
import sk.jakubvanko.commoncore.ConfigManager;
import sk.jakubvanko.commoncore.IClickActionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UCConfigManager extends ConfigManager {

    public UCConfigManager(String configPath, IClickActionFactory clickActionFactory) {
        super(configPath, clickActionFactory);
    }

    @Override
    public ConfigData loadData() {
        ConfigData configData = super.loadData();
        Map<ItemStack, Map<ItemStack, Integer>> chestSettingsMap = loadChestSettings(configData.getItemStackMap());
        List<String> chestIdentifiers = loadChestIdentifiers();
        Map<ItemStack, List<RewardData>> rewardSettingsMap = loadRewardSettings(configData.getItemStackMap());
        return new UCConfigData(configData, chestSettingsMap, chestIdentifiers, rewardSettingsMap);
    }

    private Map<ItemStack, Map<ItemStack, Integer>> loadChestSettings(Map<String, ItemStack> itemStackMap) {
        Map<ItemStack, Map<ItemStack, Integer>> chestSettingsMap = new HashMap<>();
        ConfigurationSection chestSettingsSection = config.getConfigurationSection("general.chest_settings");
        for (String chestID : chestSettingsSection.getKeys(false)) {
            Map<ItemStack, Integer> tierData = new HashMap<>();
            ConfigurationSection tierSettingsSection = chestSettingsSection.getConfigurationSection(chestID);
            for (String tierID : tierSettingsSection.getKeys(false)) {
                Integer tierChance = tierSettingsSection.getInt(tierID);
                tierData.put(itemStackMap.get(tierID), tierChance);
            }
            chestSettingsMap.put(itemStackMap.get(chestID), tierData);
        }
        return chestSettingsMap;
    }

    private List<String> loadChestIdentifiers() {
        ConfigurationSection chestSettingsSection = config.getConfigurationSection("general.chest_settings");
        return new ArrayList<>(chestSettingsSection.getKeys(false));
    }

    private Map<ItemStack, List<RewardData>> loadRewardSettings(Map<String, ItemStack> itemStackMap) {
        Map<ItemStack, List<RewardData>> rewardSettingsMap = new HashMap<>();
        ConfigurationSection tierSettingsSection = config.getConfigurationSection("general.reward_settings");
        for (String rewardTierID : tierSettingsSection.getKeys(false)) {
            ConfigurationSection rewardSettingsSection = tierSettingsSection.getConfigurationSection(rewardTierID);
            List<RewardData> rewardDataList = new ArrayList<>();
            for (String rewardID : rewardSettingsSection.getKeys(false)) {
                ConfigurationSection rewardDataSection = rewardSettingsSection.getConfigurationSection(rewardID);
                int chance = rewardDataSection.getInt("chance", 0);
                int min = rewardDataSection.getInt("min_amount", 1);
                int max = rewardDataSection.getInt("max_amount", 1);
                ItemStack reward = itemStackMap.get(rewardID);
                RewardData rewardData = new RewardData(reward, chance, min, max);
                rewardDataList.add(rewardData);
            }
            rewardSettingsMap.put(itemStackMap.get(rewardTierID), rewardDataList);
        }
        return rewardSettingsMap;
    }


}

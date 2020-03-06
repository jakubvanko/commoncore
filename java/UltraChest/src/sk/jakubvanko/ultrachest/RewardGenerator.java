package sk.jakubvanko.ultrachest;

import org.bukkit.inventory.ItemStack;
import sk.jakubvanko.commoncore.CCMaterial;

import java.util.List;
import java.util.Map;

public class RewardGenerator {

    private UCConfigData configData;

    public RewardGenerator(UCConfigData configData) {
        this.configData = configData;
    }

    public ItemStack generateItemFromMap(Map<ItemStack, Integer> itemChanceMap) {
        if (itemChanceMap == null) return new ItemStack(CCMaterial.AIR.parseMaterial());
        int totalChance = 0;
        for (Integer chance : itemChanceMap.values()) {
            totalChance += chance;
        }
        int randomNumber = (int) Math.ceil(Math.random() * totalChance);
        for (Map.Entry<ItemStack, Integer> entry : itemChanceMap.entrySet()) {
            randomNumber -= entry.getValue();
            if (randomNumber <= 0) {
                ItemStack rewardItem = entry.getKey();
                if (rewardItem == null) return new ItemStack(CCMaterial.AIR.parseMaterial());
                return new ItemStack(rewardItem);
            }
        }
        return new ItemStack(CCMaterial.AIR.parseMaterial());
    }

    public ItemStack generateRewardTier(ItemStack ultraChestItem) {
        Map<ItemStack, Integer> chestTierMap = configData.getChestSettingsMap().get(ultraChestItem);
        return generateItemFromMap(chestTierMap);
    }

    public ItemStack generateReward(ItemStack rewardTierItem) {
        List<RewardData> rewardDataList = configData.getRewardSettingsMap().get(rewardTierItem);
        if (rewardDataList == null) return new ItemStack(CCMaterial.AIR.parseMaterial());
        int totalChance = 0;
        for (RewardData rewardData : rewardDataList) {
            totalChance += rewardData.getChance();
        }
        int randomNumber = (int) Math.ceil(Math.random() * totalChance);
        for (RewardData rewardData : rewardDataList) {
            randomNumber -= rewardData.getChance();
            if (randomNumber <= 0) {
                ItemStack reward = new ItemStack(rewardData.getReward());
                int randomAmount = (int) Math.ceil(Math.random() * rewardData.getMaximum()) + rewardData.getMinimum() - 1;
                reward.setAmount(randomAmount);
                return reward;
            }
        }
        return new ItemStack(CCMaterial.AIR.parseMaterial());
    }
}

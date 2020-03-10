package sk.jakubvanko.ultrachest;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class RewardGenerator {

    private UCConfigData configData;

    RewardGenerator(UCConfigData configData) {
        this.configData = configData;
    }

    ItemStack generateItemFromMap(Map<ItemStack, Integer> itemChanceMap) {
        if (itemChanceMap == null) return XMaterial.AIR.parseItem();
        int totalChance = 0;
        for (Integer chance : itemChanceMap.values()) {
            totalChance += chance;
        }
        int randomNumber = (int) Math.ceil(Math.random() * totalChance);
        for (Map.Entry<ItemStack, Integer> entry : itemChanceMap.entrySet()) {
            randomNumber -= entry.getValue();
            if (randomNumber <= 0) {
                ItemStack rewardItem = entry.getKey();
                if (rewardItem == null) return XMaterial.AIR.parseItem();
                return new ItemStack(rewardItem);
            }
        }
        return XMaterial.AIR.parseItem();
    }

    ItemStack generateRewardTier(ItemStack ultraChestItem) {
        Map<ItemStack, Integer> chestTierMap = configData.getChestSettingsMap().get(ultraChestItem);
        return generateItemFromMap(chestTierMap);
    }

    public ItemStack generateReward(ItemStack rewardTierItem) {
        List<RewardData> rewardDataList = configData.getRewardSettingsMap().get(rewardTierItem);
        if (rewardDataList == null) return XMaterial.AIR.parseItem();
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
        return XMaterial.AIR.parseItem();
    }
}

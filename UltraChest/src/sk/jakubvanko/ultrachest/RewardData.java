package sk.jakubvanko.ultrachest;

import org.bukkit.inventory.ItemStack;

public class RewardData {

    private int chance;
    private int minimum;
    private int maximum;
    private ItemStack reward;

    public ItemStack getReward() {
        return reward;
    }

    public int getChance() {
        return chance;
    }

    public int getMinimum() {
        return minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public RewardData(ItemStack reward, int chance, int minimum, int maximum){
        this.reward = reward;
        this.chance = chance;
        this.minimum = minimum;
        this.maximum = maximum;
    }
}

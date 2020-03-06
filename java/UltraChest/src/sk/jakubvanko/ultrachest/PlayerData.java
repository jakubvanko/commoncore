package sk.jakubvanko.ultrachest;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import sk.jakubvanko.commoncore.InventoryData;

import java.util.List;

public class PlayerData {

    private Player player;
    private ItemStack chestItem;
    private int choicesLeft;
    private InventoryData playerInventoryData;
    private List<Integer> clickedSlots;
    private RewardRevealer rewardRevealer;

    public Player getPlayer() {
        return player;
    }

    public ItemStack getChestItem() {
        return chestItem;
    }

    public int getChoicesLeft() {
        return choicesLeft;
    }

    public void setChoicesLeft(int choicesLeft) {
        this.choicesLeft = choicesLeft;
    }

    public InventoryData getPlayerInventoryData() {
        return playerInventoryData;
    }

    public List<Integer> getClickedSlots() {
        return clickedSlots;
    }

    public RewardRevealer getRewardRevealer() {
        return rewardRevealer;
    }

    public void setRewardRevealer(RewardRevealer rewardRevealer) {
        this.rewardRevealer = rewardRevealer;
    }

    public PlayerData(Player player, ItemStack chestItem, int choicesLeft, InventoryData playerInventoryData, List<Integer> clickedSlots) {
        this.player = player;
        this.chestItem = chestItem;
        this.choicesLeft = choicesLeft;
        this.playerInventoryData = playerInventoryData;
        this.clickedSlots = clickedSlots;
    }
}

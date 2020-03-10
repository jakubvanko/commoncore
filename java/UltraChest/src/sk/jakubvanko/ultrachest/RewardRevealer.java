package sk.jakubvanko.ultrachest;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import sk.jakubvanko.commoncore.ClickAction;
import sk.jakubvanko.ultrachest.actions.GenerateReward;

import java.util.ArrayList;
import java.util.List;

public class RewardRevealer extends BukkitRunnable {

    private int currentSlot = 0;
    private PlayerData playerData;
    private XSound sound;
    private RewardGenerator rewardGenerator;

    public RewardRevealer(RewardGenerator rewardGenerator, PlayerData playerData, XSound sound) {
        this.playerData = playerData;
        this.sound = sound;
        this.rewardGenerator = rewardGenerator;
    }

    @Override
    public void run() {
        if (currentSlot < playerData.getPlayerInventoryData().getInventory().getSize()) {
            // Removes old actions
            playerData.getPlayerInventoryData().getSlotActionMap().remove(currentSlot);
            // Generates a reward tier for every slot
            ItemStack rewardTier = rewardGenerator.generateRewardTier(playerData.getChestItem());
            playerData.getPlayerInventoryData().getInventory().setItem(currentSlot, rewardTier);
            // For some slots also generates new actions
            if (playerData.getClickedSlots().contains(currentSlot)) {
                List<ClickAction> newActions = new ArrayList<>();
                // This action does not need any arguments, because it does not use them
                newActions.add(new GenerateReward(null));
                playerData.getPlayerInventoryData().getSlotActionMap().put(currentSlot, newActions);
            }
            if (sound != null) {
                sound.playSound(playerData.getPlayer(), 10, 1);
            }
            currentSlot++;
        } else {
            this.cancel();
            // Removes reward tiers that were not chosen by the player
            for (int i = 0; i < playerData.getPlayerInventoryData().getInventory().getSize(); i++){
                if (!playerData.getClickedSlots().contains(i)) {
                    playerData.getPlayerInventoryData().getInventory().setItem(i, XMaterial.AIR.parseItem());
                }
            }
        }
    }

    void finishRunning() {
        while (currentSlot < playerData.getPlayerInventoryData().getInventory().getSize()) {
            run();
        }
    }
}

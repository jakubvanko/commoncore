package sk.jakubvanko.ultrachest.actions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import sk.jakubvanko.commoncore.CCSound;
import sk.jakubvanko.commoncore.ClickAction;
import sk.jakubvanko.ultrachest.PlayerData;
import sk.jakubvanko.ultrachest.RewardRevealer;
import sk.jakubvanko.ultrachest.UCEventArguments;

import java.util.List;
import java.util.Map;

public class UltraChestStart extends ClickAction<UCEventArguments> {

    public UltraChestStart(Map<String, Object> arguments) {
        super(arguments);
    }

    @Override
    public void onSlotClicked(UCEventArguments eventArguments) {
        InventoryClickEvent event = eventArguments.getEvent();
        PlayerData playerData = eventArguments.getPlayerData();
        int playerChoicesLeft = playerData.getChoicesLeft();
        if (playerChoicesLeft > 0) {
            List<Integer> clickedSlots = playerData.getClickedSlots();
            int clickedSlot = event.getSlot();
            if (clickedSlots.contains(clickedSlot)) return;
            playerData.setChoicesLeft(playerChoicesLeft - 1);
            playerData.getClickedSlots().add(clickedSlot);
            eventArguments.getPlayerData().getPlayerInventoryData().getSlotActionMap().remove(clickedSlot);
            if (playerChoicesLeft - 1 == 0) {
                //Removing all actions from every slot
                for (int i = 0; i < event.getClickedInventory().getSize(); i++) {
                    eventArguments.getPlayerData().getPlayerInventoryData().getSlotActionMap().remove(i);
                }
                String soundName = getArgumentString("REVEALING_SOUND", null);
                CCSound sound;
                if (soundName == null) sound = null;
                else {
                    sound = CCSound.fromString(soundName);
                }
                RewardRevealer rewardRevealer = new RewardRevealer(eventArguments.getRewardGenerator(), playerData, sound);
                playerData.setRewardRevealer(rewardRevealer);
                Plugin plugin = eventArguments.getPlugin();
                int revealingDelay = getArgumentInt("REVEALING_DELAY", 1);
                rewardRevealer.runTaskTimer(plugin, 0, revealingDelay);
            }
        }
    }
}

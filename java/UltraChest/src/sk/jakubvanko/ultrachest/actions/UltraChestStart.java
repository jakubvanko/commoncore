package sk.jakubvanko.ultrachest.actions;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import sk.jakubvanko.commoncore.ClickAction;
import sk.jakubvanko.ultrachest.PlayerData;
import sk.jakubvanko.ultrachest.RewardRevealer;
import sk.jakubvanko.ultrachest.UCEventArguments;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
                Inventory clickedInventory;
                if (XMaterial.isNewVersion()) {
                    clickedInventory = event.getClickedInventory();
                } else {
                    clickedInventory = event.getView().getTopInventory();
                }
                //Removing all actions from every slot
                for (int i = 0; i < clickedInventory.getSize(); i++) {
                    eventArguments.getPlayerData().getPlayerInventoryData().getSlotActionMap().remove(i);
                }
                String soundName = getArgumentString("REVEALING_SOUND", null);
                XSound sound;
                if (soundName == null) sound = null;
                else {
                    Optional<XSound> optionalXSound = XSound.matchXSound(soundName);
                    sound = optionalXSound.get();
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

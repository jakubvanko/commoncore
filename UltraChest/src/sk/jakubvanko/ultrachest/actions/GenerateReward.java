package sk.jakubvanko.ultrachest.actions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import sk.jakubvanko.commoncore.CCMaterial;
import sk.jakubvanko.commoncore.ClickAction;
import sk.jakubvanko.commoncore.actions.GiveItem;
import sk.jakubvanko.commoncore.actions.RemoveItem;
import sk.jakubvanko.ultrachest.UCEventArguments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenerateReward extends ClickAction<UCEventArguments> {

    public GenerateReward(Map<String, Object> arguments) {
        super(arguments);
    }

    @Override
    public void onSlotClicked(UCEventArguments eventArguments) {
        InventoryClickEvent event = eventArguments.getEvent();
        ItemStack rewardTier = event.getCurrentItem();
        // Generating a reward
        ItemStack generatedReward = eventArguments.getRewardGenerator().generateReward(rewardTier);
        int clickedSlot = event.getSlot();
        Inventory clickedInventory;
        if (CCMaterial.isNewVersion()) {
            clickedInventory = event.getClickedInventory();
        } else {
            clickedInventory = event.getView().getTopInventory();
        }
        clickedInventory.setItem(clickedSlot, generatedReward);
        // Removing this action and replacing it with others
        Map<Integer, List<ClickAction>> slotActionMap = eventArguments.getPlayerData().getPlayerInventoryData().getSlotActionMap();
        slotActionMap.remove(clickedSlot);
        List<ClickAction> newActions = new ArrayList<>();
        // These actions do not need any arguments, because they do not use them
        newActions.add(new GiveItem(null));
        newActions.add(new RemoveItem(null));
        slotActionMap.put(clickedSlot, newActions);
    }
}

package sk.jakubvanko.commoncore.actions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import sk.jakubvanko.commoncore.ClickAction;
import sk.jakubvanko.commoncore.EventArguments;

import java.util.Map;

/**
 * Represents a give item click action
 */
public class GiveItem extends ClickAction<EventArguments> {
    /**
     * Creates a give item click action with the given arguments
     *
     * @param arguments Map of action arguments
     */
    public GiveItem(Map<String, Object> arguments) {
        super(arguments);
    }

    /**
     * Gives the clicked item to the player that triggered the event
     *
     * @param eventArguments Additional arguments passed at runtime
     */
    @Override
    public void onSlotClicked(EventArguments eventArguments) {
        Player player = eventArguments.getPlayer();
        ItemStack clickedItem = new ItemStack(eventArguments.getEvent().getCurrentItem());
        player.getInventory().addItem(clickedItem);
    }
}

package sk.jakubvanko.commoncore.actions;

import org.bukkit.inventory.ItemStack;
import sk.jakubvanko.commoncore.ClickAction;
import sk.jakubvanko.commoncore.EventArguments;

import java.util.Map;

/**
 * Represents a replace item click action
 */
public class ReplaceItem extends ClickAction<EventArguments> {
    /**
     * Creates a replace item click action with the given arguments
     *
     * @param arguments Map of action arguments
     */
    public ReplaceItem(Map<String, Object> arguments) {
        super(arguments);
    }

    /**
     * Replaces the clicked item with a specified item
     *
     * @param eventArguments Additional arguments passed at runtime
     */
    @Override
    public void onSlotClicked(EventArguments eventArguments) {
        String itemIdentifier = getArgumentString("ITEM", null);
        if (itemIdentifier == null) return;
        ItemStack item = eventArguments.getConfigData().getItemStackMap().get(itemIdentifier);
        if (item == null) return;
        eventArguments.getEvent().setCurrentItem(item);
    }
}

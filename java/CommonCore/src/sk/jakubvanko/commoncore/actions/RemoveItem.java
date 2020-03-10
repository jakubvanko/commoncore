package sk.jakubvanko.commoncore.actions;

import com.cryptomorin.xseries.XMaterial;
import sk.jakubvanko.commoncore.ClickAction;
import sk.jakubvanko.commoncore.EventArguments;

import java.util.Map;

/**
 * Represents a remove item click action
 */
public class RemoveItem extends ClickAction<EventArguments> {
    /**
     * Creates a remove item click action with the given arguments
     *
     * @param arguments Map of action arguments
     */
    public RemoveItem(Map<String, Object> arguments) {
        super(arguments);
    }

    /**
     * Removes the clicked item from the inventory
     *
     * @param eventArguments Additional arguments passed at runtime
     */
    @Override
    public void onSlotClicked(EventArguments eventArguments) {
        eventArguments.getEvent().setCurrentItem(XMaterial.AIR.parseItem());
    }
}

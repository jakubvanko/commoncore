package sk.jakubvanko.commoncore.actions;

import sk.jakubvanko.commoncore.ClickAction;
import sk.jakubvanko.commoncore.EventArguments;

import java.util.Map;

/**
 * Represents a close inventory click action
 */
public class CloseInventory extends ClickAction<EventArguments> {
    /**
     * Creates a close inventory click action with the given arguments
     *
     * @param arguments Map of action arguments
     */
    public CloseInventory(Map<String, Object> arguments) {
        super(arguments);
    }

    /**
     * Closes the inventory of the player that triggered the event
     *
     * @param eventArguments Additional arguments passed at runtime
     */
    @Override
    public void onSlotClicked(EventArguments eventArguments) {
        eventArguments.getPlayer().closeInventory();
    }
}

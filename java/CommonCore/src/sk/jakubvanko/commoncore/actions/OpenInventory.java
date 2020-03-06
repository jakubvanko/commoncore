package sk.jakubvanko.commoncore.actions;

import org.bukkit.inventory.Inventory;
import sk.jakubvanko.commoncore.ClickAction;
import sk.jakubvanko.commoncore.EventArguments;

import java.util.Map;

/**
 * Represents an open inventory click action
 */
public class OpenInventory extends ClickAction<EventArguments> {
    /**
     * Creates an open inventory click action with the given arguments
     * Needs an argument called INVENTORY_NAME
     *
     * @param arguments Map of action arguments
     */
    public OpenInventory(Map<String, Object> arguments) {
        super(arguments);
    }

    /**
     * Opens the specified inventory for the player that triggered this event
     * Needs a map of inventory identifiers linked to inventory data holders
     *
     * @param eventArguments Additional arguments passed at runtime
     */
    @Override
    public void onSlotClicked(EventArguments eventArguments) {
        String inventoryName = getArgumentString("INVENTORY_NAME", "main_menu");
        Inventory inventoryToOpen = eventArguments.getConfigData().getInventoryDataMap().get(inventoryName).getInventory();
        eventArguments.getPlayer().openInventory(inventoryToOpen);
    }
}

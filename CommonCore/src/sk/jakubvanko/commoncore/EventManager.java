package sk.jakubvanko.commoncore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Represents an event manager
 */
public abstract class EventManager implements Listener {

    protected ConfigData configData;

    /**
     * Creates a new event manager with a given config data holder
     *
     * @param configData Config data holder
     */
    public EventManager(ConfigData configData) {
        this.configData = configData;
    }

    /**
     * Event that triggers inventory click actions
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = checkInventoryClickEvent(event);
        if (clickedInventory == null) return;
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) return;
        if (CCMaterial.AIR.isSameMaterial(clickedItem)) return;
        for (InventoryData inventoryData : configData.inventoryDataMap.values()) {
            Inventory checkedInventory = inventoryData.getInventory();
            if (clickedInventory.equals(checkedInventory)) {
                Integer clickedSlot = event.getSlot();
                inventoryData.triggerActions(clickedSlot, generateArguments(event));
                event.setCancelled(true);
            }
        }
    }

    /**
     * Checks whether an inventory click event interacted with a GUI
     *
     * @param event Event to check
     * @return Inventory that was checked or null if the inventory is invalid for gui
     */
    protected Inventory checkInventoryClickEvent(InventoryClickEvent event) {
        Inventory clickedInventory;
        if (CCMaterial.isNewVersion()) {
            clickedInventory = event.getClickedInventory();
        } else {
            clickedInventory = event.getInventory();
            if (event.getRawSlot() >= clickedInventory.getSize()) return null;
        }
        if (clickedInventory == null) return null;
        if (clickedInventory.getHolder() != null) return null;
        if (!(event.getWhoClicked() instanceof Player)) return null;
        return clickedInventory;
    }

    /**
     * Generates additional arguments for click actions
     *
     * @param event Event that triggered the creation of these arguments
     * @return Additional event arguments generated at runtime
     */
    protected abstract EventArguments generateArguments(InventoryClickEvent event);
}

package sk.jakubvanko.commoncore;

import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Map;

/**
 * Represents an inventory data holder
 */
public final class InventoryData {

    private Inventory inventory;

    private Map<Integer, List<ClickAction>> slotActionMap;

    /**
     * Gets the linked inventory
     *
     * @return Linked inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets the linked inventory
     *
     * @param inventory Inventory to set as linked
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Gets a map representing inventory slots linked to different actions
     *
     * @return Map representing inventory slots and corresponding actions
     */
    public Map<Integer, List<ClickAction>> getSlotActionMap() {
        return slotActionMap;
    }

    /**
     * Sets the map representing inventory slots linked to different actions
     *
     * @param slotActionMap Map representing inventory slots and corresponding actions
     */
    public void setSlotActionMap(Map<Integer, List<ClickAction>> slotActionMap) {
        this.slotActionMap = slotActionMap;
    }

    /**
     * Creates a new inventory data holder
     *
     * @param inventory     Linked inventory
     * @param slotActionMap Map representing inventory slots linked to different actions
     */
    public InventoryData(Inventory inventory, Map<Integer, List<ClickAction>> slotActionMap) {
        this.inventory = inventory;
        this.slotActionMap = slotActionMap;
    }

    /**
     * Triggers all actions for a given inventory slot
     *
     * @param clickedSlot    Inventory slot number to trigger all actions of
     * @param eventArguments Additional arguments specified at runtime
     */
    public void triggerActions(Integer clickedSlot, EventArguments eventArguments) {
        List<ClickAction> slotActions = slotActionMap.get(clickedSlot);
        if (slotActions == null) return;
        for (ClickAction clickAction : slotActions) {
            clickAction.onSlotClicked(eventArguments);
        }
    }
}

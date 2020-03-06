package sk.jakubvanko.commoncore;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Represents additional event arguments holder
 */
public abstract class EventArguments {
    private InventoryClickEvent event;
    private Player player;
    private ConfigData configData;

    /**
     * Gets the inventory click event associated with these arguments
     *
     * @return Inventory click event that triggered the creation of these arguments
     */
    public InventoryClickEvent getEvent() {
        return event;
    }

    /**
     * Gets the player associated with these arguments
     *
     * @return Player that triggered the creation of these arguments
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the config data associated with these arguments
     *
     * @return Config data associated with these arguments
     */
    public ConfigData getConfigData() {
        return configData;
    }

    /**
     * Creates an additional event arguments holder
     *
     * @param event      Event that triggered the creation of these arguments
     * @param configData Config data of the event manager
     */
    public EventArguments(InventoryClickEvent event, ConfigData configData) {
        this.event = event;
        this.player = (Player) event.getWhoClicked();
        this.configData = configData;
    }
}

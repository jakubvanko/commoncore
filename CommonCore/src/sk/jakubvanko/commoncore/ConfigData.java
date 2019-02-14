package sk.jakubvanko.commoncore;

import org.bukkit.configuration.Configuration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Map;

/**
 * Represents a config data holder
 * Holds information that was loaded from config
 */
public class ConfigData {

    protected Map<String, ItemStack> itemStackMap;
    protected Map<String, Recipe> recipeMap;
    protected Map<String, InventoryData> inventoryDataMap;
    protected Map<String, String> messageMap;
    protected Configuration config;

    /**
     * Gets the map of item identifiers and their corresponding item stacks
     *
     * @return Map of item identifiers and their corresponding item stacks
     */
    public Map<String, ItemStack> getItemStackMap() {
        return itemStackMap;
    }

    /**
     * Gets the map of recipe identifiers and their corresponding recipes
     *
     * @return Map of recipe identifiers and their corresponding recipes
     */
    public Map<String, Recipe> getRecipeMap() {
        return recipeMap;
    }

    /**
     * Gets the map of inventory data identifiers and their corresponding inventory data holders
     *
     * @return Map of inventory data identifiers and their corresponding inventory data holders
     */
    public Map<String, InventoryData> getInventoryDataMap() {
        return inventoryDataMap;
    }

    /**
     * Gets the map of message identifier linked to their interpretations
     *
     * @return Map of message identifier linked to their interpretations
     */
    public Map<String, String> getMessageMap() {
        return messageMap;
    }

    /**
     * Gets the configuration associated with this config data holder
     *
     * @return Configuration associated with this config data holder
     */
    public Configuration getConfig() {
        return config;
    }

    /**
     * Creates a config data holder
     *
     * @param config           Configuration associated with this config data holder
     * @param itemStackMap     Map of item identifiers and their corresponding item stacks
     * @param recipeMap        Map of recipe identifiers and their corresponding recipes
     * @param inventoryDataMap Map of inventory data identifiers and their corresponding inventory data holders
     * @param messageMap       Map of message identifier linked to their interpretations
     */
    public ConfigData(Configuration config, Map<String, ItemStack> itemStackMap, Map<String, Recipe> recipeMap, Map<String, InventoryData> inventoryDataMap, Map<String, String> messageMap) {
        this.itemStackMap = itemStackMap;
        this.recipeMap = recipeMap;
        this.inventoryDataMap = inventoryDataMap;
        this.messageMap = messageMap;
        this.config = config;
    }
}

package sk.jakubvanko.commoncore;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * Represents a config manager and loader
 */
public class ConfigManager {

    protected File configFile;
    protected Configuration config;
    protected IClickActionFactory clickActionFactory;

    /**
     * Creates a config manager
     *
     * @param configPath         Path to the configuration to link
     * @param clickActionFactory Factory for creating click actions from their names
     */
    public ConfigManager(String configPath, IClickActionFactory clickActionFactory) {
        configFile = new File(configPath);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe("Error: Unable to create config file " + configFile.getName());
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(configFile);
        this.clickActionFactory = clickActionFactory;
    }

    /**
     * Loads all data from config
     *
     * @return Config data holder
     */
    public ConfigData loadData() {
        Map<String, ItemStack> itemStackMap = loadItemStacks();
        Map<String, Recipe> recipeMap = loadRecipes(itemStackMap);
        Map<String, InventoryData> inventoryDataMap = loadInventories(itemStackMap);
        Map<String, String> messageMap = loadMessages();
        return new ConfigData(config, itemStackMap, recipeMap, inventoryDataMap, messageMap);
    }

    /**
     * Registers the given itemstack into the item section of the config
     *
     * @param itemStack Itemstack to register
     * @return String representing the item identifier
     */
    public String registerItem(ItemStack itemStack) {
        String itemIdentifier = itemStack.getType().name().toLowerCase() + "_" + itemStack.hashCode();
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> itemData = new ArrayList<>();
        itemData.add("  " + itemIdentifier + ":");
        if (itemMeta.hasDisplayName()) {
            itemData.add("    name: '" + itemMeta.getDisplayName() + "'");
        }
        itemData.add("    material: " + itemStack.getType().name());
        itemData.add("    amount: " + itemStack.getAmount());
        // This assures compatibility with older versions
        if (CCMaterial.isNewVersion()) {
            Damageable damageable = (Damageable) itemMeta;
            itemData.add("    damage: " + damageable.getDamage());
            if (itemMeta.isUnbreakable()) {
                itemData.add("    unbreakable: " + "true");
            }
        }
        List<String> loreList = itemMeta.getLore();
        if (loreList != null) {
            String loreTitle = loreList.size() != 0 ? "    lore:" : "    lore: []";
            itemData.add(loreTitle);
            for (String line : loreList) {
                String formattedLine = "    - '" + line + "'";
                itemData.add(formattedLine);
            }
        }
        Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();
        if (enchantments != null) {
            itemData.add("    enchantments:");
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                itemData.add("      " + entry.getKey().getName() + ": " + entry.getValue());
            }
        }
        Set<ItemFlag> itemFlags = itemMeta.getItemFlags();
        if (itemFlags != null) {
            String itemFlagTitle = itemFlags.size() != 0 ? "    item_flags:" : "    item_flags: []";
            itemData.add(itemFlagTitle);
            for (ItemFlag flag : itemFlags) {
                String formattedFlagLine = "    - '" + flag.name() + "'";
                itemData.add(formattedFlagLine);
            }
        }
        try {
            List<String> allLines = Files.readAllLines(configFile.toPath());
            for (int i = 0; i < allLines.size(); i++) {
                if (allLines.get(i).equals("items:")) {
                    allLines.addAll(i + 1, itemData);
                    break;
                }
            }
            Files.write(configFile.toPath(), allLines); // You can add a charset and other options too
        } catch (Exception e) {
            Bukkit.getServer().getLogger().severe("Error: Unable to edit config file " + configFile.getName());
        }
        // Reloading the configuration
        this.config = YamlConfiguration.loadConfiguration(configFile);
        return itemIdentifier;
    }

    /**
     * Loads the map of item identifiers and their corresponding item stacks from the config
     *
     * @return Map of item identifiers and their corresponding item stacks
     */
    protected Map<String, ItemStack> loadItemStacks() {
        Map<String, ItemStack> itemMap = new HashMap<>();
        ConfigurationSection itemSection = config.getConfigurationSection("items");
        if (itemSection == null) return itemMap;
        for (String itemIdentifier : itemSection.getKeys(false)) {
            ConfigurationSection dataSection = itemSection.getConfigurationSection(itemIdentifier);
            String name = dataSection.getString("name", null);
            String materialName = dataSection.getString("material", "AIR");
            CCMaterial ccMaterial = CCMaterial.fromString(materialName);
            int amount = dataSection.getInt("amount", 1);
            int damage = dataSection.getInt("damage", 0);
            boolean unbreakable = dataSection.getBoolean("unbreakable", false);
            List<String> lore = dataSection.getStringList("lore");
            List<String> itemFlagStrings = dataSection.getStringList("item_flags");
            List<ItemFlag> itemFlags = new ArrayList<>();
            if (itemFlagStrings != null) {
                for (String itemFlagString : itemFlagStrings) {
                    ItemFlag itemFlag = ItemFlag.valueOf(itemFlagString);
                    itemFlags.add(itemFlag);
                }
            }
            ConfigurationSection enchantmentSection = dataSection.getConfigurationSection("enchantments");
            Map<Enchantment, Integer> enchantments = new HashMap<>();
            if (enchantmentSection != null) {
                for (String enchantmentName : enchantmentSection.getKeys(false)) {
                    Enchantment enchantment = Enchantment.getByName(enchantmentName);
                    Integer level = enchantmentSection.getInt(enchantmentName, 1);
                    enchantments.put(enchantment, level);
                }
            }
            ItemBuilder itemBuilder = new ItemBuilder(ccMaterial)
                    .setAmount(amount)
                    .setDamage(damage)
                    .setUnbreakable(unbreakable)
                    .setLore(lore)
                    .setItemFlags(itemFlags)
                    .setEnchantments(enchantments);
            if (name != null){
                itemBuilder.setName(name);
            }
            ItemStack createdItem = itemBuilder.build();
            itemMap.put(itemIdentifier, createdItem);
        }
        return itemMap;
    }

    /**
     * Loads the map of recipe identifiers and their corresponding recipes from the config
     *
     * @param itemStackMap Map of item identifiers and their corresponding item stacks
     * @return Map of recipe identifiers and their corresponding recipes
     */
    protected Map<String, Recipe> loadRecipes(Map<String, ItemStack> itemStackMap) {
        Map<String, Recipe> recipeMap = new HashMap<>();
        ConfigurationSection recipeSection = config.getConfigurationSection("recipes");
        if (recipeSection == null) return recipeMap;
        Map<Character, CCMaterial> ingredientsMap = new HashMap<>();
        // Loading ingredients
        ConfigurationSection ingredientSection = recipeSection.getConfigurationSection("ingredients");
        if (ingredientSection == null) return recipeMap;
        for (String ingredientSymbol : ingredientSection.getKeys(false)) {
            Character ingredientCharacter = ingredientSymbol.charAt(0);
            CCMaterial ingredient = CCMaterial.fromString(ingredientSection.getString(ingredientSymbol));
            ingredientsMap.put(ingredientCharacter, ingredient);
        }
        // Creating shaped recipes
        ConfigurationSection shapedRecipeSection = recipeSection.getConfigurationSection("shaped_recipes");
        if (shapedRecipeSection != null) {
            for (String itemIdentifier : shapedRecipeSection.getKeys(false)) {
                ConfigurationSection recipeRowSection = shapedRecipeSection.getConfigurationSection(itemIdentifier);
                ItemStack result = itemStackMap.get(itemIdentifier);
                ShapedRecipe shapedRecipe = new ShapedRecipe(result);
                String recipeRow1 = recipeRowSection.getString("row_1", "   ");
                String recipeRow2 = recipeRowSection.getString("row_2", "   ");
                String recipeRow3 = recipeRowSection.getString("row_3", "   ");
                shapedRecipe.shape(recipeRow1, recipeRow2, recipeRow3);
                for (Map.Entry<Character, CCMaterial> ingredientEntry : ingredientsMap.entrySet()) {
                    shapedRecipe.setIngredient(ingredientEntry.getKey(), ingredientEntry.getValue().parseMaterial());
                }
                recipeMap.put(itemIdentifier, shapedRecipe);
            }
        }
        // Creating shapeless recipes
        ConfigurationSection shapelessRecipeSection = recipeSection.getConfigurationSection("shapeless_recipes");
        if (shapelessRecipeSection != null) {
            for (String itemIdentifier : shapelessRecipeSection.getKeys(false)) {
                ItemStack result = itemStackMap.get(itemIdentifier);
                String ingredientString = shapelessRecipeSection.getString(itemIdentifier);
                ShapelessRecipe shapelessRecipe = new ShapelessRecipe(result);
                for (Character ingredientCharacter : ingredientString.toCharArray()) {
                    shapelessRecipe.addIngredient(ingredientsMap.get(ingredientCharacter).parseMaterial());
                }
                recipeMap.put(itemIdentifier, shapelessRecipe);
            }
        }
        return recipeMap;
    }

    /**
     * Loads the map of inventory data identifiers and their corresponding inventory data holders from the config
     *
     * @param itemStackMap Map of item identifiers and their corresponding item stacks
     * @return Map of inventory data identifiers and their corresponding inventory data holders
     */
    protected Map<String, InventoryData> loadInventories(Map<String, ItemStack> itemStackMap) {
        Map<String, InventoryData> inventoryMap = new HashMap<>();
        ConfigurationSection inventorySection = config.getConfigurationSection("inventories");
        // Creating chest inventories
        ConfigurationSection chestInventorySection = inventorySection.getConfigurationSection("chest_inventories");
        if (chestInventorySection != null) {
            for (String inventoryIdentifier : chestInventorySection.getKeys(false)) {
                ConfigurationSection dataSection = chestInventorySection.getConfigurationSection(inventoryIdentifier);
                Inventory chestInventory = createChestInventory(dataSection);
                ConfigurationSection contentSection = dataSection.getConfigurationSection("content");
                InventoryData inventoryData = setupChestInventory(chestInventory, contentSection, itemStackMap);
                inventoryMap.put(inventoryIdentifier, inventoryData);
            }
        }
        return inventoryMap;
    }

    /**
     * Creates chest inventory from a given configuration section
     *
     * @param dataSection Configuration section containing title and size
     * @return New, empty chest inventory
     */
    protected Inventory createChestInventory(ConfigurationSection dataSection) {
        int size = dataSection.getInt("size", 1);
        String title = dataSection.getString("title", "not_specified");
        return Bukkit.createInventory(null, size, title);
    }

    /**
     * Setups the chest inventory as inventory data holder with actions and items loaded from config
     *
     * @param chestInventory Inventory to be filled
     * @param contentSection Configuration section containing the contents of the inventory
     * @param itemStackMap   Map of item identifiers and their corresponding item stacks
     * @return Complete inventory data holder
     */
    protected InventoryData setupChestInventory(Inventory chestInventory, ConfigurationSection contentSection, Map<String, ItemStack> itemStackMap) {
        // Each inventory slot has a number of actions that will be called on click
        Map<Integer, List<ClickAction>> slotToActionMap = new HashMap<>();
        for (String inventorySlot : contentSection.getKeys(false)) {
            // Filling the inventory
            Integer inventorySlotNumber = Integer.parseInt(inventorySlot);
            ConfigurationSection slotSection = contentSection.getConfigurationSection(inventorySlot);
            String itemIdentifier = slotSection.getString("item");
            ItemStack itemIcon = itemStackMap.get(itemIdentifier);
            chestInventory.setItem(inventorySlotNumber, itemIcon);
            List<ClickAction> clickActions = new ArrayList<>();
            ConfigurationSection actionSection = slotSection.getConfigurationSection("actions");
            // Creating Click Actions
            if (actionSection != null) {
                for (String actionName : actionSection.getKeys(false)) {
                    ConfigurationSection actionArgumentSection = actionSection.getConfigurationSection(actionName);
                    Map<String, Object> argumentMap = new HashMap<>();
                    // Mapping action argument names to arguments
                    if (actionArgumentSection != null) {
                        for (String actionArgumentName : actionArgumentSection.getKeys(false)) {
                            Object actionArgument = actionArgumentSection.get(actionArgumentName);
                            argumentMap.put(actionArgumentName, actionArgument);
                        }
                    }
                    ClickAction clickAction = clickActionFactory.getClickAction(actionName, argumentMap);
                    clickActions.add(clickAction);
                }
                // Mapping inventory slots to actions
                slotToActionMap.put(inventorySlotNumber, clickActions);
            }
        }
        return new InventoryData(chestInventory, slotToActionMap);
    }

    /**
     * Gets the map of message identifier linked to their interpretations from the config
     *
     * @return Map of message identifier linked to their interpretations
     */
    protected Map<String, String> loadMessages() {
        Map<String, String> messageMap = new HashMap<>();
        ConfigurationSection messageSection = config.getConfigurationSection("messages");
        if (messageSection == null) return messageMap;
        for (String messageIdentifier : messageSection.getKeys(false)) {
            String message = messageSection.getString(messageIdentifier);
            messageMap.put(messageIdentifier, message);
        }
        return messageMap;
    }

    /**
     * Replaces abbreviations in a lore with their substitutes
     *
     * @param itemstack     Target itemstack
     * @param abbreviations Map of abbreviations and their substitutes
     */
    protected void formatLoreAbbreviations(ItemStack itemstack, Map<String, String> abbreviations) {
        ItemMeta itemMeta = itemstack.getItemMeta();
        List<String> oldLore = itemMeta.getLore();
        for (int i = 0; i < oldLore.size(); i++) {
            for (String abbreviation : abbreviations.keySet()) {
                oldLore.set(i, oldLore.get(i).replace(abbreviation, abbreviations.get(abbreviation)));
            }
        }
        itemMeta.setLore(oldLore);
        itemstack.setItemMeta(itemMeta);
    }
}

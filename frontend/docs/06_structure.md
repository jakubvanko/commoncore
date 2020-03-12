---
id: 06_structure
title: Plugin Structure
sidebar_label: Plugin Structure
---
All CommonCore plugins have a similar basic code structure.

## Main plugin class boilerplate
```java
package your.package_name.here;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import sk.jakubvanko.commoncore.ConfigData;
import sk.jakubvanko.commoncore.MessageManager;
import sk.jakubvanko.commoncore.Metrics;
import sk.jakubvanko.commoncore.VaultManager;

import java.util.Map;

public class YourPlugin extends JavaPlugin {

    private MessageManager messageManager;

    @Override
    public void onEnable() {
        // Make sure to save the configuration
        saveDefaultConfig();
        // MyConfigManager inherits from CommonCore ConfigManager
        MyConfigManager configManager = new MyConfigManager(...);
        // Load data from the configuration
        ConfigData configData = configManager.loadData();
        // Register recipes from the configuration
        for (Map.Entry<String, Recipe> recipeEntry : configData.getRecipeMap().entrySet()) {
            Bukkit.getServer().addRecipe(recipeEntry.getValue());
        }
        // Create a MessageManager using loaded messages
        messageManager = new MessageManager(configData.getMessageMap());
        // Create a VaultManager (if needed)
        VaultManager vaultManager = new VaultManager(messageManager);
        // Create an EventManager and register him
        PluginManager pluginManager = getServer().getPluginManager();
        // MyEventManager inherits from CommonCore EventManager
        MyEventManager eventManager = new MyEventManager(...);
        pluginManager.registerEvents(eventManager, this);
        // Register optional metrics
        Metrics metrics = new Metrics(this);
        // Register commands and other things that your plugin needs
    }
}
```

## Configuration manager
A class that is used to interact with configurations. After calling its function "".loadData()", it retuns a ConfigData object holding all other objects loaded from the configuration file. It generally needs to be inherited in another class that modifies some parts of its behaviour.

#### Usage example
```java
// MyConfigManager inherits from CommonCore ConfigManager
MyConfigManager configManager = new MyConfigManager(...);
// Load data from the configuration
ConfigData configData = configManager.loadData();
```

### ConfigData object
ConfigData holds all objects loaded from the configuration. Its functions are in the form of getters and can be used to get the desired objects.

#### Usage example
```java
// Get messages loaded from the configuration
Map<String, String> messageMap = configData.getMessageMap();
```

## Inventory data
All inventories are loaded as inventoryData object that holds not only the given inventory but also all information about the click actions belonging to the inventory slots.

### Click actions
All inventory click actions have to be inherited from ClickAction class. Click actions are created in a ClickActionFactory object that needs to implement IClickActionFactory interface. This factory is used to create click actions based on strings in the configuration.

## Event manager
Manages events for inventory GUIs as well as any other events needed by the plugin. It generally also needs to be inherited in another class that modifies some parts of its behaviour. After an inventory click event is triggered, it creates an object EventArguments that is passed to the inventory click actions in the given inventory. EventArguments also needs to be inherited and modified to suit the requirements of the plugin.


## Utility classes

### Item builder
Simplifies the creation of ItemStacks.

#### Usage example
```java
ItemBuilder itemBuilder = new ItemBuilder(xMaterial)
                              .setAmount(amount)
                              .setDamage(damage)
                              .setUnbreakable(unbreakable)
                              .setLore(lore)
                              .setItemFlags(itemFlags)
                              .setEnchantments(enchantments);
ItemStack createdItem = itemBuilder.build();
```

### Message manager
Manages messages and abbreviations. As many message managers as needed can be created, however, one is usually enough as it should be passed to constructors of other classes.

#### Usage example
```java
// Logs a message into the console
messageManager.logMessage("The plugin was safely deactivated");
```

## Included external utilities

### Vault manager
Simplifies the interaction with Economy plugins using Vault.

### Metrics by BStats
A class that can be used to collect information about the usage of the plugin. It can be activated by creating it in the onEnable() function of the plugin.

### XSeries utilities
All XSeries classes are bundled in the CommonCore library. Visit the XSeries documentation to learn more about these utilties.

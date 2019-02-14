package sk.jakubvanko.ultrachest;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import sk.jakubvanko.commoncore.MessageManager;
import sk.jakubvanko.commoncore.Metrics;
import sk.jakubvanko.commoncore.VaultManager;

import java.util.Map;

public class UltraChest extends JavaPlugin {

    private MessageManager messageManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        UCConfigManager configManager = new UCConfigManager(getDataFolder().getPath() + "/config.yml", new UCClickActionFactory());
        UCConfigData configData = (UCConfigData) configManager.loadData();
        // Registering recipes
        for (Map.Entry<String, Recipe> recipeEntry : configData.getRecipeMap().entrySet()) {
            Bukkit.getServer().addRecipe(recipeEntry.getValue());
        }
        messageManager = new MessageManager(configData.getMessageMap());
        VaultManager vaultManager = new VaultManager(messageManager);
        // Setting up event manager and registering events
        PluginManager pluginManager = getServer().getPluginManager();
        UCEventManager eventManager = new UCEventManager(this, configData, vaultManager);
        pluginManager.registerEvents(eventManager, this);
        // Registering commands
        getCommand("ultrachest").setExecutor(new UCCommand(configManager, configData));
        // Registering optional metrics
        Metrics metrics = new Metrics(this);
    }

    @Override
    public void onDisable() {
        messageManager.logMessage("Plugin was safely deactivated");
    }
}

package sk.jakubvanko.betterbeacons;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import sk.jakubvanko.commoncore.ConfigData;
import sk.jakubvanko.commoncore.MessageManager;
import sk.jakubvanko.commoncore.Metrics;
import sk.jakubvanko.commoncore.VaultManager;

import java.util.Map;

public class BetterBeacons extends JavaPlugin {

    private EffectTimeManager effectTimeManager;
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        BBConfigManager configManager = new BBConfigManager(getDataFolder().getPath() + "/config.yml", new BBClickActionFactory());
        ConfigData configData = configManager.loadData();
        // Registering recipes
        for (Map.Entry<String, Recipe> recipeEntry : configData.getRecipeMap().entrySet()) {
            Bukkit.getServer().addRecipe(recipeEntry.getValue());
        }
        messageManager = new MessageManager(configData.getMessageMap());
        VaultManager vaultManager = new VaultManager(messageManager);
        // Setting up effect time manager
        effectTimeManager = new EffectTimeManager(configData);
        effectTimeManager.loadBeaconEffects();
        if (getConfig().getBoolean("general.effect_duration.enabled")) {
            long period = getConfig().getLong("general.effect_duration.subtraction_time");
            effectTimeManager.runTaskTimer(this, 0, period * 20);
        }
        // Setting up event manager and registering events
        PluginManager pluginManager = getServer().getPluginManager();
        BBEventManager eventManager = new BBEventManager(configData, effectTimeManager, vaultManager);
        pluginManager.registerEvents(eventManager, this);
        // Registering commands
        getCommand("betterbeacons").setExecutor(new BBCommand(configData));
        // Registering optional metrics
        Metrics metrics = new Metrics(this);
    }

    public void onDisable() {
        if (getConfig().getBoolean("effect_duration.enabled")) {
            effectTimeManager.cancel();
        }
        messageManager.logMessage("Plugin was safely deactivated");
    }
}

package sk.jakubvanko.ultrachest;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import sk.jakubvanko.commoncore.EventArguments;
public class UCEventArguments extends EventArguments {

    private UCConfigData ucConfigData;
    private PlayerData playerData;
    private Plugin plugin;
    private RewardGenerator rewardGenerator;

    @Override
    public UCConfigData getConfigData() {
        return ucConfigData;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public RewardGenerator getRewardGenerator() {
        return rewardGenerator;
    }

    public UCEventArguments(InventoryClickEvent event, UCConfigData configData, Plugin plugin, RewardGenerator rewardGenerator, PlayerData playerData) {
        super(event, configData);
        this.ucConfigData = configData;
        this.plugin = plugin;
        this.rewardGenerator = rewardGenerator;
        this.playerData = playerData;
    }
}

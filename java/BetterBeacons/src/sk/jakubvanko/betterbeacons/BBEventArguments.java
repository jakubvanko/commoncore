package sk.jakubvanko.betterbeacons;

import org.bukkit.block.Beacon;
import org.bukkit.event.inventory.InventoryClickEvent;
import sk.jakubvanko.commoncore.ConfigData;
import sk.jakubvanko.commoncore.EventArguments;
import sk.jakubvanko.commoncore.MessageManager;
import sk.jakubvanko.commoncore.VaultManager;

public class BBEventArguments extends EventArguments {

    private MessageManager messageManager;
    private EffectTimeManager effectTimeManager;
    private Beacon beacon;
    private VaultManager vaultManager;

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public EffectTimeManager getEffectTimeManager() {
        return effectTimeManager;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public VaultManager getVaultManager() {
        return vaultManager;
    }

    public BBEventArguments(InventoryClickEvent event, ConfigData configData, MessageManager messageManager, EffectTimeManager effectTimeManager, Beacon beacon, VaultManager vaultManager) {
        super(event, configData);
        this.messageManager = messageManager;
        this.effectTimeManager = effectTimeManager;
        this.beacon = beacon;
        this.vaultManager = vaultManager;
    }
}

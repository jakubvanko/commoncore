package sk.jakubvanko.commoncore;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

/**
 * Represents a vault manager used to work with other vault plugins
 */
public class VaultManager {

    protected Economy economy;

    /**
     * Creates a vault manager
     *
     * @param messageManager Message manager used to log messages
     */
    public VaultManager(MessageManager messageManager) {
        economy = loadVault(messageManager);
    }

    /**
     * Loads Vault
     *
     * @param messageManager Message manager used to log messages
     * @return Economy or null if Vault not found
     */
    protected Economy loadVault(MessageManager messageManager) {
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            messageManager.logMessage("Vault found! Connecting...");
            RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (economyProvider != null && economyProvider.getProvider() != null) {
                messageManager.logMessage("Connection to Vault was successful!");
                return economyProvider.getProvider();
            } else {
                messageManager.logMessage("Economy Provider not found! Disabling economy support...");
            }
        } else {
            messageManager.logMessage("Vault not found! Disabling economy support...");
        }
        return null;
    }

    /**
     * Tries to withdraw money and returns the result (True if Economy is null)
     *
     * @param player Player to withdraw money from
     * @param money  Amount of money to withdraw
     * @return Boolean representing the result of the transaction
     */
    public boolean withdrawMoney(Player player, int money) {
        if (economy == null) return true;
        if (money <= 0) return true;
        return economy.withdrawPlayer(player, money).transactionSuccess();
    }

    /**
     * Tries to deposit money and returns the result (False if Economy is null)
     *
     * @param player Player to deposit money to
     * @param money  Amount of money to deposit
     * @return Boolean representing the result of the transaction
     */
    public boolean depositMoney(Player player, int money){
        if (economy == null) return false;
        if (money <= 0) return true;
        return economy.depositPlayer(player, money).transactionSuccess();
    }
}

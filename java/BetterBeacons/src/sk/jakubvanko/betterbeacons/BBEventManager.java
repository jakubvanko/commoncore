package sk.jakubvanko.betterbeacons;

import org.bukkit.block.Beacon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import sk.jakubvanko.commoncore.*;

import java.util.HashMap;
import java.util.Map;

public class BBEventManager extends EventManager {

    private MessageManager messageManager;
    private EffectTimeManager effectTimeManager;
    private VaultManager vaultManager;
    private Map<Player, Beacon> beaconMap;

    private boolean needsTool;
    private boolean normalInteraction;

    public BBEventManager(ConfigData configData, EffectTimeManager effectTimeManager, VaultManager vaultManager) {
        super(configData);
        messageManager = new MessageManager(configData.getMessageMap());
        this.effectTimeManager = effectTimeManager;
        this.vaultManager = vaultManager;
        beaconMap = new HashMap<>();

        needsTool = configData.getConfig().getBoolean("general.tool.needed");
        normalInteraction = configData.getConfig().getBoolean("general.normal_beacon_interaction");
    }

    @Override
    protected EventArguments generateArguments(InventoryClickEvent inventoryClickEvent) {
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        return new BBEventArguments(inventoryClickEvent, configData, messageManager, effectTimeManager, beaconMap.get(player), vaultManager);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() != CCMaterial.BEACON.parseMaterial()) return;
        Player player = event.getPlayer();
        if (!(player.hasPermission("betterbeacons.usebeacon"))) {
            messageManager.sendMessage("no_permission", player, null);
            event.setCancelled(true);
            return;
        }
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand == null) {
            event.setCancelled(true);
            return;
        }
        ItemStack tool = configData.getItemStackMap().get("TOOL");
        if (needsTool && (!itemInHand.equals(tool))) {
            if (normalInteraction) return;
            // If the player is trying to use another item on the beacon while sneaking, maybe there is another plugin
            // that interacts with beacons so don't cancel the event
            if (!(player.isSneaking()) || (CCMaterial.AIR.parseMaterial() == itemInHand.getType())) {
                event.setCancelled(true);
            }
            return;
        }
        // If there is no tool and player is using an item on the beacon while sneaking, maybe there is another plugin
        // that interacts with beacons so don't cancel the event
        else if (!needsTool && player.isSneaking() && !CCMaterial.AIR.isSameMaterial(itemInHand)) return;
        Beacon beacon = (Beacon) event.getClickedBlock().getState();
        beaconMap.put(player, beacon);
        player.openInventory(configData.getInventoryDataMap().get("main_menu").getInventory());
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        Player player = (Player) event.getPlayer();
        Inventory setupMenu = configData.getInventoryDataMap().get("setup_menu").getInventory();
        Inventory mainMenu = configData.getInventoryDataMap().get("main_menu").getInventory();
        if (event.getInventory().equals(setupMenu) || event.getInventory().equals(mainMenu)) return;
        beaconMap.remove(player);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        if (event.getBlock() == null) return;
        if (event.getBlock().getType() != CCMaterial.BEACON.parseMaterial()) return;
        Beacon beacon = (Beacon) event.getBlock().getState();
        effectTimeManager.removeBeaconEffect(beacon, true, true);
    }
}

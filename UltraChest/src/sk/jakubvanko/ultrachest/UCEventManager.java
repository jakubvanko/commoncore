package sk.jakubvanko.ultrachest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import sk.jakubvanko.commoncore.*;
import sk.jakubvanko.ultrachest.actions.GenerateReward;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UCEventManager extends EventManager {

    private MessageManager messageManager;
    private UCConfigData configData;
    private Map<Player, PlayerData> playerDataMap;
    private int choiceAmount;
    private boolean interactToOpen;
    private Plugin plugin;
    private RewardGenerator rewardGenerator;

    public UCEventManager(Plugin plugin, UCConfigData configData) {
        super(configData);
        this.configData = configData;
        messageManager = new MessageManager(configData.getMessageMap());
        this.plugin = plugin;
        choiceAmount = configData.getConfig().getInt("general.choice_amount");
        interactToOpen = configData.getConfig().getBoolean("general.interact_to_open");
        playerDataMap = new HashMap<>();
        rewardGenerator = new RewardGenerator(configData);
    }

    @Override
    protected EventArguments generateArguments(InventoryClickEvent inventoryClickEvent) {
        return new UCEventArguments(inventoryClickEvent, configData, plugin, rewardGenerator, playerDataMap.get((Player) inventoryClickEvent.getWhoClicked()));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (interactToOpen) return;
        startUCEvent(event, event.getPlayer(), event.getItemInHand());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            ItemStack eventItem = event.getItem();
            if (eventItem == null) return;
            if (eventItem.getType().isBlock() && !interactToOpen) return;
            startUCEvent(event, event.getPlayer(), eventItem);
        }
    }

    private void startUCEvent(Cancellable event, Player player, ItemStack eventItem) {
        for (ItemStack chestItem : configData.getChestSettingsMap().keySet()) {
            if (chestItem.isSimilar(eventItem)) {
                event.setCancelled(true);
                if (!player.hasPermission("ultrachest.open")) {
                    messageManager.sendMessage("no_permission", player, null);
                    return;
                }
                InventoryData generatedInventoryData = generateInventoryData();
                player.openInventory(generatedInventoryData.getInventory());
                PlayerData playerData = new PlayerData(player, chestItem, choiceAmount, generatedInventoryData, new ArrayList<>());
                playerDataMap.put(player, playerData);
                eventItem.setAmount(eventItem.getAmount() - 1);
                event.setCancelled(true);
                return;
            }
        }
    }

    private InventoryData generateInventoryData() {
        InventoryData defaultInventoryData = configData.getInventoryDataMap().get("ultrachest_menu");
        Inventory defaultInventory = defaultInventoryData.getInventory();
        Inventory newInventory = Bukkit.createInventory(null, defaultInventory.getSize(), defaultInventory.getTitle());
        for (int i = 0; i < defaultInventory.getSize(); i++) {
            if (defaultInventory.getItem(i) == null) continue;
            if (defaultInventory.getItem(i).getType() == CCMaterial.AIR.parseMaterial()) continue;
            newInventory.setItem(i, new ItemStack(defaultInventory.getItem(i)));
        }
        Map<Integer, List<ClickAction>> slotActionMap = defaultInventoryData.getSlotActionMap();
        Map<Integer, List<ClickAction>> newSlotActionMap = new HashMap<>(slotActionMap);
        return new InventoryData(newInventory, newSlotActionMap);
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = checkInventoryClickEvent(event);
        if (clickedInventory == null) return;
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) return;
        if (CCMaterial.AIR.isSameMaterial(clickedItem)) return;
        Player player = (Player) event.getWhoClicked();
        if (player == null) return;
        PlayerData playerData = playerDataMap.get(player);
        if (playerData == null) return;
        InventoryData playerInventoryData = playerData.getPlayerInventoryData();
        if (playerInventoryData == null) return;
        if (clickedInventory.equals(playerInventoryData.getInventory())) {
            Integer clickedSlot = event.getSlot();
            playerInventoryData.triggerActions(clickedSlot, generateArguments(event));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        if (inventory == null) return;
        PlayerData playerData = playerDataMap.get(player);
        if (playerData == null) return;
        InventoryData ultraChestInventoryData = playerData.getPlayerInventoryData();
        if (ultraChestInventoryData == null) return;
        if (!ultraChestInventoryData.getInventory().equals(inventory)) return;
        if (playerData.getChoicesLeft() > 0) {
            ItemStack newChestItem = new ItemStack(playerData.getChestItem());
            newChestItem.setAmount(1);
            player.getInventory().addItem(newChestItem);
        } else {
            RewardRevealer rewardRevealer = playerData.getRewardRevealer();
            int taskId = rewardRevealer.getTaskId();
            if (Bukkit.getScheduler().isCurrentlyRunning(taskId) || Bukkit.getScheduler().isQueued(taskId)) {
                rewardRevealer.finishRunning();
            }
            Map<Integer, List<ClickAction>> slotActionMap = playerData.getPlayerInventoryData().getSlotActionMap();
            for (int i = 0; i < inventory.getSize(); i++) {
                List<ClickAction> slotActions = slotActionMap.get(i);
                if (slotActions == null) continue;
                for (ClickAction clickAction : slotActions) {
                    //If it is a reward tier, generate a reward for it
                    if (clickAction instanceof GenerateReward) {
                        ItemStack rewardTier = inventory.getItem(i);
                        inventory.setItem(i, rewardGenerator.generateReward(rewardTier));
                    }
                }
                ItemStack currentItem = inventory.getItem(i);
                if (currentItem == null) continue;
                if (currentItem.getType() == CCMaterial.AIR.parseMaterial()) continue;
                player.getInventory().addItem(currentItem);
            }
        }
    }
}

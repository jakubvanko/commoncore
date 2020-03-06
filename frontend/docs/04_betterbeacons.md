---
id: 04_betterbeacons
title: Better Beacons
sidebar_label: Better Beacons
---
Normal beacons can only transmit a few of the vast amount of potion effects in Minecraft. Better Beacons is a plugin that unlocks their full potential by improving and increasing the functionality of beacons. It is very flexible and fully configurable through the config. This plugin allows server administrators to specify the effects that beacons can transmit and even set up their prices.

## Features
- Beacons can transmit all effects that exist in Minecraft
- Different needed beacons tiers can be set for different effects
- Different prices (either item price or money) can be set for different effects
- Beacons can be set to only transmit effects for a specified period of time
- Administrators can set whether players need to use special tool to interact with beacons
- Players can get exact information about any beacon
- Players can choose to modify only the primary or the secondary effect
- Economy support - [Vault](https://dev.bukkit.org/projects/vault) needed
- Special permissions
- Complete configurability

## Commands
The default command is "/betterbeacons". Its aliases are "/bb" and "/betterbeacon". Once used, it will tell the player how to use the commands of Better Beacons plugin.

### /betterbeacons tool
This command gives you the tool needed to use beacons. The tool will work properly only if the configuration disallows using beacons without the tool. You can also specify a player name as an argument (For example "/bb tool LolokarSK"), which will give the tool to the specified player.

## Permissions
|Permission|Description|Default|
|----------|-----------|-------|
|betterbeacons.usebeacon|Allows interaction with beacons|Everyone|
|betterbeacons.givetool|Allows /betterbeacons tool command|OP|

## Configuration
This part of this document describes the configuration of Better Beacons plugin. Before proceeding further, please make sure that you understand general [CommonCore plugin configuration.](https://commoncore.jakubvanko.com/docs/03_configuration)

**Configuration is located at:** <code>plugins\BetterBeacons\config.yml</code>

#### Configuration paths
This documentation section uses configuration paths which are just concise representations of subordinate keys.

For example, the path "**general:tool:needed**" means:
```yml
general:
  tool:
    needed: VALUE
```

### General settings
This configuration section is used to specify the general settings of the plugin. It has the following paths:

|Configuration path|Description|Possible variables|Default variable|
|------------------|-----------|------------------|----------------|
|general:tool:needed|Declares whether players have to use the special tool to interact with beacons.|true/false|**true**|
|general:normal_beacon_interaction|Declares whether players can use beacons the normal way (like in normal Minecraft).|true/false|**false** (true may break the plugin)|
|general:effect_duration:enabled|Declares whether beacons should stop transmitting effects after a specified period of time.|true/false|**false**|
|general:effect_duration:duration|Time in seconds after which the beacon stops transmitting effects (can be stacked by being activated multiple times).|Integer (number of seconds)|**86400** (= 24 real hours)|
|general:effect_duration:subtraction_time|Time in seconds after which server checks and deactivates beacons that have no more duration.|Integer (number of seconds)|**60** (the larger the number, the smaller the server load)|

Better Beacons also incorporates [bStats](https://bstats.org/) to collect anonymous information about its usefulness.

### Messages
Messages are closely documented in the CommonCore tutorial. All messages support color formatting using the paragraph **§** symbol. The default variable type for all messages is string (if not stated otherwise).

#### Logo
Config path: <span style={{color: "darkgreen"}}>**messages:logo**</span>

Text representing the plugin logo that will be at the front of every logged message in the console. You can insert this message into other messages using the %logo abbreviation.

#### Secondary set - low tier
Config path: <span style={{color: "darkgreen"}}>**messages:secondary_set_low_tier**</span>

Text sent to the player when they successfully set secondary beacon effect but the beacon is not tier 4.

#### Secondary set - no primary
Config path: <span style={{color: "darkgreen"}}>**messages:secondary_set_no_primary**</span>

Text sent to the player when they successfully set secondary beacon effect but the beacon does not have a primary effect.

#### Low tier
Config path: <span style={{color: "darkgreen"}}>**messages:low_tier**</span>

Text sent to the player when they try to set an effect that has a higher needed beacon tier than the current one.

#### No permission
Config path: <span style={{color: "darkgreen"}}>**messages:no_permission**</span>

Text sent to the player when they try to do something that they don't have permissions for.

#### Player not found
Config path: <span style={{color: "darkgreen"}}>**messages:player_not_found**</span>

Text that sent to the player when they try to target a player that is currently offline (using the /betterbeacons tool command).

#### Not enough items
Config path: <span style={{color: "darkgreen"}}>**messages:not_enough_items**</span>

Text that sent to the player when they try to set a beacon effect that costs more items than they have in their inventory.

#### Not enough money
Config path: <span style={{color: "darkgreen"}}>**messages:not_enough_money**</span>

Text sent to the player when they try to set a beacon effect that costs more money than they have.

#### Wrong arguments
Config path: <span style={{color: "darkgreen"}}>**messages:wrong_args**</span>

Text that sent to the player when they try to use the /betterbeacons command with invalid arguments.

#### Default command
Config path: <span style={{color: "darkgreen"}}>**messages:default_command**</span>

Text sent to the player when they use the /betterbeacons command without any arguments. The variable type for this message is a **multi-line string**.

#### Beacon information
Config path: <span style={{color: "darkgreen"}}>**messages:beacon_info**</span>

Text that sent to the player after they click on an item with the action BEACON_INFO. The variable type for this message is a **multi-line string**. This message also supports the following abbreviations:

|Abbreviation|Description|
|------------|-----------|
|%x|X-coordinate of the beacon|
|%y|Y-coordinate of the beacon|
|%z|Z-coordinate of the beacon|
|%bt|Current beacon tier (number)|
|%pe|Current primary beacon effect|
|%se|Current secondary beacon effect|
|%pd|Remaining duration of the primary effect (in seconds)|
|%sd|Remaining duration of the secondary effect (in seconds)|

##### Beacon information example
If the following message is set in a plugin configuration:
```yml
messages:
  beacon_info: |
    %logo
    Beacon is located at  X: %x,  Y: %y,  Z: %z
    Tier:  %bt
    Primary effect:  %pe
    Secondary effect:  %se
```
The message sent to a player may be for example:
```
[BetterBeacons]
Beacon is located at:  X: 225,  Y: -587,  Z: 1147
Tier: 4
Primary effect:  speed
Secondary effect:  none
```

### Items
Item settings are closely documented in the CommonCore tutorial. The only ItemStack that is very important is defined under the "**TOOL**" item identifier and it represents the tool needed to set up beacons (if allowed in general settings). If you want to define an item as a price for a beacon effect, you do not have to specify enchantments or item flags because it only looks at the material and the amount (name property can be also used in the %price abbreviation).

#### Lore abbreviation
If an item is located in a CommonCore defined inventory on a slot with click action SET_EFFECT, its lore will support also the following abbreviations:

|Abbreviation|Description|
|------------|-----------|
|%nt|Needed tier to set the beacon effect|
|%price|The list of all item prices in separate rows|

### Recipes
Recipes are closely documented in the CommonCore tutorial.

### Inventories
Inventory settings are closely documented in the CommonCore tutorial. This configuration section takes advantage of advanced YAML concepts. Please, make sure to also learn about node anchors and node references before proceeding further.

This plugin makes use of the following inventories that you can modify as any other CommonCore inventory:

|Inventory identifier|Description|
|--------------------|-----------|
|main_menu|The first inventory displayed to the player when they use a beacon|
|primary_menu|Inventory where players can choose the primary effect of the beacon|
|secondary_menu|Inventory where players can choose the secondary effect of the beacon|
|setup_menu|Inventory where players can choose the primary effect of the beacon and after choosing they will be moved to the secondary menu|

### Inventory click actions
This plugin supports the following inventory click actions that you can specify in the inventory section of the configuration to modify the logic of the plugin inventory GUIs.

#### OPEN_INVENTORY
Opens the specified inventory.

|Argument name|Argument values|
|--------|------|
|INVENTORY_NAME|main_menu, primary_menu, secondary_menu, setup_menu or any other inventory registered in the inventory section|

#### REMOVE_BEACON_EFFECT
Removes the specified effects from the beacon.

|Argument name|Argument values|
|--------|------|
|PRIMARY|true/false (should remove primary effect?)|
|SECONDARY|true/false (should remove secondary effect?)|

#### BEACON_INFO
Writes the specified message to the player once they try to find out information about a beacon.

|Argument name|Argument values|
|--------|------|
|MESSAGE|Any registered message identifier can be used, however, the message identifier "**beacon_info**" is recommended|

#### CLOSE_INVENTORY
Closes the specified inventory.

|Argument name|Argument values|
|--------|------|
|INVENTORY_NAME|main_menu, primary_menu, secondary_menu, setup_menu or any other inventory registered in the inventory section|

#### SET_EFFECT
Sets the beacon up to transmit the specified effect.

|Argument name|Argument values|
|--------|------|
|EFFECT|[Bukkit potion effect type (see below)](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionEffectType.html)|
|TIER|Number (representing the tier needed to apply this effect)|
|ITEM_PRICE|YAML list of item identifiers representing the needed items to set up this beacon effect (every item identifier must be on a new line)
|MONEY_PRICE|Number (representing the money price of this effect)|
|MONEY_PRICE_MESSAGE|String (message that will be added to the lore in the %price abbreviation)|

##### Potion effect types

|Potion effect type|Description|
|------------------|-----------|
|ABSORPTION|Increases the maximum health of an entity with health that cannot be regenerated, but is refilled every 30 seconds.|
|BAD_OMEN|oof.|
|BLINDNESS|Blinds an entity.|
|CONDUIT_POWER|Effects granted by a nearby conduit.|
|CONFUSION|Warps vision on the client.|
|DAMAGE_RESISTANCE|Decreases damage dealt to an entity.|
|DOLPHINS_GRACE|Squee'ek uh'k kk'kkkk squeek eee'eek.|
|FAST_DIGGING|Increases dig speed.|
|FIRE_RESISTANCE|Stops fire damage.|
|GLOWING|Outlines the entity so that it can be seen from afar.|
|HARM|Hurts an entity.|
|HEAL|Heals an entity.|
|HEALTH_BOOST|Increases the maximum health of an entity.|
|HERO_OF_THE_VILLAGE|\o/.|
|HUNGER|Increases hunger.|
|INCREASE_DAMAGE|Increases damage dealt.|
|INVISIBILITY|Grants invisibility.|
|JUMP|Increases jump height.|
|LEVITATION|Causes the entity to float into the air.|
|LUCK|Loot table luck.|
|NIGHT_VISION|Allows an entity to see in the dark.|
|POISON|Deals damage to an entity over time.|
|REGENERATION|Regenerates health.|
|SATURATION|Increases the food level of an entity each tick.|
|SLOW|Decreases movement speed.|
|SLOW_DIGGING|Decreases dig speed.|
|SLOW_FALLING|Slows entity fall rate.|
|SPEED|Increases movement speed.|
|UNLUCK|Loot table unluck.|
|WATER_BREATHING|Allows breathing underwater.|
|WEAKNESS|Decreases damage dealt by an entity.|
|WITHER|Deals damage to an entity over time.|

Full list of potion effect types can be found [using this link.](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html)

## Minecraft limitations
Some Minecraft mechanics and Bukkit support limit this plugin in the following ways:
- Beacon tier needed for secondary effect to work is always **4**
- You can't set only secondary effect - it will activate only once the primary effect is set
- If you choose the same primary and secondary effect, it will set it to level 2
- If a beacon has the same primary and secondary effect and you want to see more information about it, it will show the secondary effect as "none"
- The lowest possible Bukkit/Spigot server version for this plugin is 1.10.2

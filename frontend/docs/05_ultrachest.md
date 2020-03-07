---
id: 05_ultrachest
title: Ultra Chests
sidebar_label: Ultra Chests
---
UltraChests is a lightweight yet complex plugin that allows server administrators to reward their players with interactive lottery-like rewards. It is also very flexible and fully configurable through the config.
![Usage gif](https://media.giphy.com/media/3og0ItEIPJymnCayQM/giphy.gif)

## Features
- Lottery-like reward system
- Full possible customization of UltraChests and rewards
- Random amounts of items can be generated
- Special permissions
- Complete configurability
- Support for [Custom Items and Textures](https://dev.bukkit.org/projects/custom-items-and-textures)

## Configuration
This part of this document describes the configuration of UltraChests plugin. Before proceeding further, please make sure that you understand general [CommonCore plugin configuration.](https://commoncore.jakubvanko.com/docs/03_configuration)

**Configuration is located at:** <code>plugins\UltraChest\config.yml</code>

#### Configuration paths
This documentation section uses configuration paths which are just concise representations of subordinate keys.

For example, the path "**general:chest_settings:**" means:
```yml
general:
  chest_settings:
    # EVERYTHING HERE
```

### General settings
The plugin works the following way:
- A player interacts with an UltraChest item (specified in the configuration)
- An UltraChest inventory is opened to the player
- The player picks a number of inventory slots by clicking on them
- The plugin generates reward tiers (rewards/prizes can be common, rare, epic etc.) based on the settings of the opened UltraChest
- Reward tiers are revealed for the chosen inventory slots
- The player clicks on the reward tiers and rewards are generated according to the settings of the reward tiers
- The UltraChest item is removed from the player's inventory

|Configuration path|Description|Possible variables|Default variable|
|------------------|-----------|------------------|----------------|
|general:choice_amount|How many inventory slots does the player need to choose|Integer (bigger than 0)|4|
|general:interact_to_open|Declares whether players have to place the UltraChest item on the ground to interact with it. (If true, only right click in the air is needed to open an UltraChest)|true/false|false|

#### Chest settings
To use an item as an UltraChest, it firstly needs to be registered in the item section of the configuration. This section is closely documented in the CommonCore tutorial.

All UltraChest settings have to be specified under the subordinate key "chest_settings" in the following way:
```yml
general:
  chest_settings:
    CHEST_IDENTIFIER_1: # CHEST_IDENTIFIER_1 is a registered item identifier
      REWARD_TIER_IDENTIFIER_1: 75  # This tier has a 75% chance
      REWARD_TIER_IDENTIFIER_2: 25  # This tier has a 25% chance
    CHEST_IDENTIFIER_2:
      REWARD_TIER_IDENTIFIER_1: 3   # This tier has a 33% chance
      REWARD_TIER_IDENTIFIER_3: 6   # This tier has a 66% chance
```
where:
- CHEST_IDENTIFIER = item identifier of the registered item that you want to use as an UltraChest
- REWARD_TIER_IDENTIFIER = item identifier of the registered item that you want to use as a reward tier icon (it will only be used as an icon in the inventory)

Every UltraChest item can have a number of possible reward tiers. A chance of the tier being generated needs to be specified for every reward tier in every UltraChest item. This chance is specified by a number which represents the likelihood of generating the given reward tier. Reward tiers with a larger number have a higher chance of being generated. The chances of all reward tiers are summed and normalized to calculate the percentage.

##### Chest settings example
```yml
general:
  chest_settings:
    common_chest_item:  # common_chest_item is an item identifier
      common_reward_tier: 75  # The common_reward_tier has a 75% chance
      rare_reward_tier: 25    # The rare_reward_tier has a 25% chance
      # common_reward_tier and rare_reward_tier are also registered item identifiers
```

#### Reward settings
This part describes the procedure of mapping rewards to the reward tiers. To use an item as a reward, it firstly needs to be registered in the item section of the configuration.

All reward settings have to be specified under the subordinate key "reward_settings" in the following way:
```yml
general:
  reward_settings:
      REWARD_TIER_IDENTIFIER_1: # REWARD_TIER_IDENTIFIER_1 is a registered item identifier
      REWARD_ITEM_IDENTIFIER_1: # REWARD_ITEM_IDENTIFIER_1 is also a registered item identifier
        chance: 1       # This reward has a 33% chance
        min_amount: 1   # Only one item will be always generated
        max_amount: 1
      REWARD_ITEM_IDENTIFIER_2:
        chance: 2       # This tier has a 67% chance
        min_amount: 2   # 2 or 3 items will be generated
        max_amount: 3
    REWARD_TIER_IDENTIFIER_2:
      REWARD_ITEM_IDENTIFIER_2:
        chance: 5       # This tier has a 100% chance (it is the only one)
        min_amount: 12  # 12 to 19 items will be generated
        max_amount: 19
```
where:
- REWARD_TIER_IDENTIFIER = item identifier of the registered item that you want to use as a reward tier icon (it will only be used as an icon in the inventory)
- REWARD_ITEM_IDENTIFIER = item identifier of the registered item that you want to use as the actual reward

Every reward tier can have a number of possible reward items (rewards). A chance of the reward item being generated needs to be specified for every reward tier. This chance is specified by a number which represents the likelihood of generating the given reward for the specified reward tier. Rewards with a larger number have a higher chance of being generated. The chances of all rewards are summed and normalized to calculate the percentage. Maximal and minimal amounts (both inclusive) also have to be specified for every reward under every reward tier as subordinate keys "min_amount" and "max_amount". Once a reward item is generated, its amount will be randomly chosen depending on these settings.

##### Reward settings example
```yml
general:
  reward_settings:
    common_reward_tier: # Settings for the common_reward_tier:
      beacon_block:     # beacon_block is a registered item identifier
        chance: 1       # it has a 33% chance of being generated
        min_amount: 1   # and the amount will be always 1
        max_amount: 1
      slime_block:      # slime_block is a registered item identifier
        chance: 2       # it has a 67% chance of being generated
        min_amount: 2   # and the amount will be between 2 and 13
        max_amount: 13
    rare_reward_tier:   # Settings for the rare_reward_tier:
      diamond_hoe:      # diamond_hoe is a registered item identifier
        chance: 5       # it has a 100% chance of being generated (it is the only one)
        min_amount: 1   # and the amount will be always 1
        max_amount: 1
```

### Messages
Messages are closely documented in the CommonCore tutorial. All messages support color formatting using the paragraph **§** symbol. The default variable type for all messages is string (if not stated otherwise).

#### Logo
Config path: <span style={{color: "darkgreen"}}>**messages:logo**</span>

Text representing the plugin logo that will be at the front of every logged message in the console. You can insert this message into other messages using the %logo abbreviation.

#### Chest not found
Config path: <span style={{color: "darkgreen"}}>**messages:chest_not_found**</span>

Text sent to the player when try to use the "/ultrachest give" command with a chest that doesn't exist.

#### Not a player
Config path: <span style={{color: "darkgreen"}}>**messages:not_player**</span>

Text sent to the command sender when they try to use the "/ultrachest register" command and they are not a player.

#### No permission
Config path: <span style={{color: "darkgreen"}}>**messages:no_permission**</span>

Text sent to the player when they try to do something that they don't have permissions for.

#### Player not found
Config path: <span style={{color: "darkgreen"}}>**messages:player_not_found**</span>

Text that sent to the player when they try to target a player that is currently offline (using the "/ultrachest give" command).

#### Wrong arguments
Config path: <span style={{color: "darkgreen"}}>**messages:wrong_args**</span>

Text that sent to the player when they try to use the "/ultrachest" command with invalid arguments.

#### Chest given
Config path: <span style={{color: "darkgreen"}}>**messages:chest_given**</span>

Text sent to the player when they successfully use the "/ultrachest give" command. This message also supports the following abbreviations:

|Abbreviation|Description|
|------------|-----------|
|%chestamount|The amount of given UltraChests|
|%playername|The player target of the command|

#### Chest listing
Config path: <span style={{color: "darkgreen"}}>**messages:chest_listing**</span>

Text that sent to the player when they use the "/ultrachest chests" command. This message also supports the following abbreviations:

|Abbreviation|Description|
|------------|-----------|
|%allchests|All registered UltraChests divided by a comma|

#### Default command
Config path: <span style={{color: "darkgreen"}}>**messages:default_command**</span>

Text sent to the player when they use the "/ultrachest" command without any arguments. The variable type for this message is a **multi-line string**.

#### Item registered
Config path: <span style={{color: "darkgreen"}}>**messages:item_registered**</span>

Text that sent to the player when they successfully use the "/ultrachest register" command. The variable type for this message is a **multi-line string**. This message also supports the following abbreviations:

|Abbreviation|Description|
|------------|-----------|
|%itemidentifier|The item identifier of the registered item (you use this identifier to find and modify the registered item in the item section of the configuration)|

### Items
Item settings are closely documented in the CommonCore tutorial. All registered items can be used as UltraChests, reward tier icons or rewards. If an item is used as a reward, the amount does not have to be specified as it is specified in the reward tier settings.

### Inventories

### Inventory click actions

## Commands
The default command is "/ultrachest". Its aliases are "/uc" and "/ultrachests". Once used, it will tell the player how to use the commands of the UltraChests plugin.

### Video tutorial
<div style={{position: "relative", paddingBottom: "56.25%", paddingTop: "25px", height: 0}}>
<iframe style={{position: "absolute", top: "0", left: "0", width: "100%", height: "100%"}} src="https://www.youtube.com/embed/9D3qv6334OI" frameborder="0" allow="accelerometer; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
</div>

## Permissions
|Permission|Description|Default|
|----------|-----------|-------|
|ultrachest.open|Allows opening of Ultrachests|Everyone|
|ultrachest.give|Allows /ultrachest give command|OP|
|ultrachest.register|Allows /ultrachest register command|OP|
|ultrachest.list|Allows /ultrachest chests command|OP|

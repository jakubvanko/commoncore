---
id: 03_configuration
title: Configuration
sidebar_label: Configuration
---

This document describes the commonalities of all CommonCore plugins. For plugin-specific information, please visit BetterBeacons or UltraChest documents.

**Configuration is located at:** <code>plugins\PluginName\config.yml</code>

## Structure

All CommonCore plugins have their configuration divided into the following sections:
- general
  - Only used by the underlying plugin.
  - Not administered by CommonCore library. (Please visit plugin-specific documents to learn how to specify settings in this section.)
- messages
  - Specify all plugin-related messages.
  - Translate the plugin to your native language
  - Use plugin-specific message abbreviations to dynamically inject up-to-date text into plugin messages.
- items
  - Define all items (ItemStacks) used by the plugin.
  - Specify all properties of ItemStacks such as enchantments, item flags or item attributes.
- recipes
  - Define recipes for the ItemStacks specified in the item section.
- inventories
  - Define plugin-related inventories using the ItemStacks specified in the item section.
  - Use plugin specific click-actions to generate inventory GUIs to simplify the usability of the plugin.

## Messages
All messages of the plugin are located in the message section. This includes plugin-specific messages as well as a number of messages that are similar across all CommonCore plugins.

### Settings
All messages support color formatting using the "paragraph" **§** symbol along with a color code. Some of the messages can also be multi-line strings. The complete settings for plugin-specific messages can be found in plugin-specific documents.

#### Color table

![Color table](https://ctrlv.sk/shots/2020/03/01/xJ5a.png)

#### Color example
"§2Hello" translates to <span style={{color: "#00aa00"}}>"Hello"</span>

### Abbreviations
CommonCore plugins also support various plugin-specific abbreviations that are usually denoted by a "percent" **%** symbol. CommonCore replaces these abbreviations with a dynamically generated text.

The only universal abbreviation supported by all CommonCore plugins is the **%logo** abbreviation that represents the logo of the plugin. This abbreviation is special as it can be overwritten by server administrators and used in other creative ways.

### General messages
Two messages of the message section are common across all CommonCore plugins.

#### Logo
```yml
messages:
    logo: "[BetterBeacons]"
```
Logo represents the logo of the plugin. Its usual form is that of a styled plugin name. This message is special as it is not usually sent to players directly but can become a part of other messages by using the %logo abbreviation.

##### Logo usage example
```yml
messages:
    logo: "[BetterBeacons]"
    wrong_args: "%logo Wrong command arguments!"
    # The sent message will be "[BetterBeacons] Wrong command arguments!"
```

#### Default command
```yml
messages:
    logo: "[BetterBeacons]"
    default_command: |
    ------------------%logo------------------
    /betterbeacons tool - Gives you the tool needed to setup beacons
```
Default Command is a message that is used by every CommonCore plugin that uses commands. It is a multi-line string and it represents a message sent to a player after they use a command in an incorrect way.

### Video tutorial
<div style={{position: "relative", paddingBottom: "56.25%", paddingTop: "25px", height: 0}}>
<iframe style={{position: "absolute", top: "0", left: "0", width: "100%", height: "100%"}} src="https://www.youtube.com/embed/ZbmzYmBAteQ" frameborder="0" allow="accelerometer; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
</div>


## Items
All items used in the given CommonCore plugin have to be specified in the item section of the configuration. Every item has its own unique item identifier that is used throughout the rest of the configuration. The item identifier is the upper-most key and consists of a sequence of characters.

Once the item identifier has been specified, you can optionally define other properties of the ItemStack. The property "material" is the only mandatory property. If you do not want to define a given property, simply do not write (or remove) it in your configuration.

### Properties
There is a number of supported item properties.

| Property | Value type |
|----------|----------|
|material|Bukkit MATERIAL|
|name|String with color formatting|
|amount|Number|
|damage|Number (if damageable)|
|unbreakable|true/false|
|lore|List of color formatted strings|
|item_flags|List of Bukkit ITEM_FLAGS|
|enchantments|*See below for an explanation*|
|item_attributes|*See below for an explanation*|

##### Item example
```yml
items:
  TOOL: # TOOL is the unique item identifier
    material: "GOLDEN_HOE"
    name: "Beacon Setter"
    amount: 1
    damage: 0
    unbreakable: true
    lore:
      - "Use this tool"
      - "on a beacon to"
      - "interact with it!"
    enchantments:
      ARROW_INFINITE: 10
    item_flags:
      - HIDE_ENCHANTS
    item_attributes:
      GENERIC_ATTACK_DAMAGE:
        HAND:
          ADD_NUMBER: 0.5
        OFF_HAND:
          ADD_SCALAR: 0.7
```

#### Material
```yml
items:
  TOOL: # TOOL is the unique item identifier
    material: "GOLDEN_HOE"
```
Specifies the material of the ItemStack. It must be a correct Bukkit material. Make sure that your Minecraft server version supports the material that you want to use. Using new materials on old Minecraft versions will break the plugin. Full list of materials can be found [using this link.](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html)

#### Name
```yml
items:
  TOOL: # TOOL is the unique item identifier
    material: "GOLDEN_HOE"
    name: "§4Beacon Setter" # The item name will be red
```
Specifies the display name of the ItemStack. It also supports color formatting using the "paragraph" **§** symbol.  

#### Amount
```yml
items:
  TOOL: # TOOL is the unique item identifier
    material: "GOLDEN_HOE"
    amount: 1 # Only one item will be needed/generated
```
Specifies the amount of the items in the ItemStack. The amount will be used for example when you use the ItemStack as a result of a recipe. If an ItemStack with an amount higher than 1 is used in other parts of the plugin, it may cause the plugin to work properly only if the player uses the exact amount of items in the exact same ItemStack.

#### Damage
```yml
items:
  TOOL: # TOOL is the unique item identifier
    material: "GOLDEN_HOE"
    damage: 0 # The item will not be damaged
```
Specifies the damage of the ItemStack. If an ItemStack with a damage higher than 0 is used in other parts of the plugin, it may cause the plugin to work properly only if the player uses the exact same ItemStack with the exact damage.

#### Unbreakable
```yml
items:
  TOOL: # TOOL is the unique item identifier
    material: "GOLDEN_HOE"
    unbreakable: true # The item will be unbreakable
```
Specifies whether the ItemStack is unbreakable. If an unbreakable ItemStack is used in other parts of the plugin, it may cause the plugin to work properly only if the player uses the exact same ItemStack that is also unbreakable.

#### Lore
```yml
items:
  TOOL: # TOOL is the unique item identifier
    material: "GOLDEN_HOE"
    lore: # A list of lore strings
      - "Use this tool"     # First lore line
      - "on a beacon to"    # Second lore line
      - "interact with it!" # Third lore line
```
Specifies the lore of the ItemStack as a YAML list of strings, where each line corresponds to one lore line. It also supports color formatting using the "paragraph" **§** symbol.

#### Enchantments
```yml
items:
  TOOL: # TOOL is the unique item identifier
    material: "GOLDEN_HOE"
    enchantments:
      ARROW_INFINITE: 10    # The item will have "Infinity X" enchantment
```
Specifies the enchantments of the ItemStack as subordinate (key: value) pairs where the key is specified as a Bukkit enchantment and the value is a number representing the level of the enchantment. Make sure that your Minecraft server version supports the enchantments that you want to use. Using new enchantments on old Minecraft versions will break the plugin. Full list of enchantments can be found [using this link.](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/enchantments/Enchantment.html)

#### Item flags
```yml
items:
  TOOL: # TOOL is the unique item identifier
    material: "GOLDEN_HOE"
    item_flags: # A list of item flags
      - HIDE_ENCHANTS       # The enchantments of the item will be hidden
```
Specifies the item flags of the ItemStack as a YAML list of Bukkit item flags. Make sure that your Minecraft server version supports the item flags that you want to use. Using item flags on old Minecraft versions will break the plugin.

##### Item flag list
| Item flag | Description |
|----------|----------|
|HIDE_ATTRIBUTES|Setting to show/hide Attributes like Damage|
|HIDE_DESTROYS|Setting to show/hide what the ItemStack can break/destroy|
|HIDE_ENCHANTS|Setting to show/hide enchants|
|HIDE_PLACED_ON|Setting to show/hide where this ItemStack can be build/placed on|
|HIDE_POTION_EFFECTS|Setting to show/hide potion effects on this ItemStack|
|HIDE_UNBREAKABLE|Setting to show/hide the unbreakable State|

Full list of item flags can be found [using this link.](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/inventory/ItemFlag.html)

#### Item attributes (Attribute Modifiers)
```yml
items:
  TOOL: # TOOL is the unique item identifier
    material: "GOLDEN_HOE"
    item_attributes:
      GENERIC_ATTACK_DAMAGE:  # The item will change the attack damage of the entity carrying it
        HAND:                 # If it is in hand:
          ADD_NUMBER: 0.5     # It will increase it by 0.5 damage
        OFF_HAND:             # If it is in the off-hand:
          ADD_SCALAR: 0.7     # It will change it by 0.7 damage
```
Specifies the item attributes of the ItemStack as subordinate (key: key: key: value) quadruples. The first key specifies Bukkit item attribute. The second key specifies Bukkit equipment slot where the attribute should take effect. The third key specifies Bukkit operation. Lastly, the value is a floating point number greater than 0 that represents the operation argument.

Item attributes work only on Minecraft 1.13+. Make sure that your Minecraft server version supports the item attributes that you want to use. Using item attributes on old Minecraft versions will break the plugin.

##### Item attribute list
| Item attribute | Description |
|----------|----------|
|GENERIC_ARMOR|Armor bonus of an Entity|
|GENERIC_ARMOR_TOUGHNESS|Armor durability bonus of an Entity|
|GENERIC_ATTACK_DAMAGE|Attack damage of an Entity|
|GENERIC_ATTACK_SPEED|Attack speed of an Entity|
|GENERIC_FLYING_SPEED|Flying speed of an Entity|
|GENERIC_FOLLOW_RANGE|Range at which an Entity will follow others|
|GENERIC_KNOCKBACK_RESISTANCE|Resistance of an Entity to knockback|
|GENERIC_LUCK|Luck bonus of an Entity|
|GENERIC_MAX_HEALTH|Maximum health of an Entity|
|GENERIC_MOVEMENT_SPEED|Movement speed of an Entity|
|HORSE_JUMP_STRENGTH|Strength with which a horse will jump|
|ZOMBIE_SPAWN_REINFORCEMENTS|Chance of a zombie to spawn reinforcements|

Full list of item attributes can be found [using this link.](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/attribute/Attribute.html)

##### Equipment slot list
| Equipment slot |
|----------------|
|HAND|
|OFF_HAND|
|HEAD|
|CHEST|
|LEGS|
|FEET|

Full list of equipment slots can be found [using this link.](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/inventory/EquipmentSlot.html)

##### Operation list
| Operation | Description |
|---------|---------------|
|ADD_NUMBER|Adds (or subtracts) the specified amount to the base value. (Operation 0 in command blocks)|
|ADD_SCALAR|Adds this scalar of amount to the base value. (Operation 1 in command blocks)|
|MULTIPLY_SCALAR_1|Multiply amount by this value, after adding 1 to it. (Operation 2 in command blocks)|

Full list of equipment slots can be found [using this link.](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/attribute/AttributeModifier.Operation.html)

### Item examples

#### Example 1
Definition in the configuration file:
```yml
items:
    common_chest_item: # common_chest_item is the unique item identifier
      material: CHEST
        name: "§fCommon §7Ultra Chest"
      lore:
        - "§ePlace this on the ground"
        - "§eand choose from mystery crates"
        - "§eto recieve your rewards" # Should be receive, sorry :)
      enchantments:
        ARROW_INFINITE: 10

```
Will result into:

![Example 1](https://ctrlv.sk/shots/2020/03/03/OOAE.png)

#### Example 2
Definition in the configuration file:
```yml
items:
  TOOL: # TOOL is the unique item identifier
    material: "GOLDEN_HOE"
    name: "§e§lBeacon Setter"
    amount: 1
    damage: 0
    unbreakable: true
    lore:
      - "§3§oUse this tool"
      - "§3§oon a beacon to"
      - "§3§ointeract with it!"
    enchantments: # The enchantment is hidden by the item flag
      ARROW_INFINITE: 10
    item_flags:
      - HIDE_ENCHANTS
```
Will result into:

![Example 2](https://ctrlv.sk/shots/2020/03/03/l5Ku.png)

#### Example 3
Definition in the configuration file:
```yml
items:
  slime_block: # slime_block is the unique item identifier
    material: "SLIME_BLOCK"
    amount: 12
```
Will result into:

![Example 3](https://ctrlv.sk/shots/2020/03/03/sbS5.png)

#### Example 4
Definition in the configuration file:
```yml
items:
  beacon_block: # beacon_block is the unique item identifier
    material: "BEACON"  # Just a normal beacon :)
```
Will result into:

![Example 4](https://ctrlv.sk/shots/2020/03/03/s4op.png)

### Video tutorial
<div style={{position: "relative", paddingBottom: "56.25%", paddingTop: "25px", height: 0}}>
<iframe style={{position: "absolute", top: "0", left: "0", width: "100%", height: "100%"}} src="https://www.youtube.com/embed/K4JprGvXQMY" frameborder="0" allow="accelerometer; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
</div>

### Custom Item and Textures (experimental)
CommonCore plugins have an experimental support for the 3rd party plugin [Custom Items and Textures](https://dev.bukkit.org/projects/custom-items-and-textures). After the successful setup of [Custom Items and Textures](https://dev.bukkit.org/projects/custom-items-and-textures), a texture for an ItemStack registered in the CommonCore plugin configuration can be provided by specifying a property **CustomItemName**. This new property should define the name of the custom item as given in the [Custom Items and Textures](https://dev.bukkit.org/projects/custom-items-and-textures) editor. **No other properties should be specified.** These items can be than used as any other ItemStacks registered in the item section of the CommonCore plugin configuration.

```yml
items:
  custom_item: # custom_item is the unique item identifier
    CustomItemName: "editor_name" # editor_name is the item name from the CIaT editor
```



## Recipes
Recipes for **only the registered ItemStacks** can be defined in the recipe section of the configuration. This section is further divided into three parts that are in the form of subordinate keys:
- ingredients
- shaped_recipes
- shapeless_recipes

#### Recipe example
```yml
recipes:
  ingredients:  # All ingredients have to be defined here
    G: GOLD_BLOCK
    /: STICK
    D: DIAMOND_BLOCK
    L: ACACIA_LOG
  shaped_recipes: # Shaped recipes for the registered item identifiers
    TOOL:
      row1: " DG" # The top row of the recipe
      row2: " / " # The mid row of the recipe
      row3: " / " # The bottom row of the recipe
  shapeless_recipes:  # Shapeless recipes for the registered item identifiers
    common_chest_item: "LLGD" # Needed ingredients for the item identifier
```

### Ingredients
All materials used in the different recipes have to be registered as ingredients. All ingredient are in the form of (key: value) pairs where the key represents the **unique ingredient symbol** and the value represents the Bukkit material corresponding to the symbol. Full list of materials can be found [using this link.](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html)
```yml
recipes:
  ingredients:  # All ingredients have to be defined here
    G: GOLD_BLOCK     # G will represent gold blocks
    /: STICK          # / will represent sticks
    D: DIAMOND_BLOCK  # D will represents diamond blocks
    L: ACACIA_LOG     # L will represent acacia logs
```

### Shaped recipes
Shaped recipes represent the crafting-table recipes where the shape of the items matter. Shaped recipes are defined in three rows (top to bottom) of three symbols where empty rows do not have to be specified. Only symbols registered in the ingredients part can be used. **The "space" symbol represents an empty cell of the crafting table.** Every shaped recipe corresponds to an item identifier representing a registered ItemStack.
```yml
recipes:
  ingredients:
    G: GOLD_BLOCK
    /: STICK
    D: DIAMOND_BLOCK
  shaped_recipes:
    TOOL: # Definition of a shaped recipe for the TOOL item
      row1: " DG" # The top row will be an empty cell, a diamond block and a gold block
      row2: " / " # The mid row will be two empty cells and a stick in the middle
      row3: " / " # The bottom row will also be two empty cells and a stick in the middle
```

### Shapeless recipes
Shapeless recipes represent the crafting-table recipes where the shape of the items **does not matter** matter. Shapeless recipes are defined as a string of 1 - 9 ingredient symbols. The "space" symbol cannot be used in a shapeless recipe. Every shapeless recipe also corresponds to an item identifier representing a registered ItemStack.
```yml
recipes:
  ingredients:  # All ingredients have to be defined here
    G: GOLD_BLOCK
    D: DIAMOND_BLOCK
    L: ACACIA_LOG
  shapeless_recipes:
    common_chest_item: "LLGD" # common_chest_item is the item identifier
    # Its recipe will be one gold block, one diamond block and two acacia logs.
```

### Recipe examples
These recipe examples represent recipes for the ItemStacks that were shown as item examples.

#### Example 1
Definition in the configuration file:
```yml
recipes:
  ingredients:
    G: GOLD_BLOCK
    /: STICK
    D: DIAMOND_BLOCK
  shaped_recipes:
    TOOL: # TOOL is the unique item identifier
      row1: " DG"
      row2: " / "
      row3: " / "
```
Will result into:

![Example 1](https://ctrlv.sk/shots/2020/03/03/TcpK.png)


#### Example 2
Definition in the configuration file:
```yml
recipes:
  ingredients:
    G: GOLD_BLOCK
    D: DIAMOND_BLOCK
  shaped_recipes:
    beacon_block: # beacon_block is the unique item identifier
      row2: "DGD"
      row3: "GGG"
```
Will result into:

![Example 2](https://ctrlv.sk/shots/2020/03/03/c0Yp.png)

#### Example 3
Definition in the configuration file:
```yml
recipes:
  ingredients:
    G: GOLD_BLOCK
    /: STICK
    D: DIAMOND_BLOCK
    C: CLAY_BALL
    L: ACACIA_LOG
  shapeless_recipes:
    common_chest_item: "LLGD" # common_chest_item is the unique item identifier
    slime_block: "CCCCCC/" # slime_block is the unique item identifier
```
Will result into:

![Example 3.1](https://ctrlv.sk/shots/2020/03/03/VoIr.png)

and into:

![Example 3.2](https://ctrlv.sk/shots/2020/03/03/JUmS.png)

### Video tutorial
<div style={{position: "relative", paddingBottom: "56.25%", paddingTop: "25px", height: 0}}>
<iframe style={{position: "absolute", top: "0", left: "0", width: "100%", height: "100%"}} src="https://www.youtube.com/embed/KwZccToZUYE" frameborder="0" allow="accelerometer; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
</div>


## Inventories
Similarly to items, all inventories used in the given CommonCore plugin have to be specified in the inventory section of the configuration.

There is a number of different inventory types. However, the only inventory type currently supported by CommonCore are chest inventories. Therefore, all inventories have to be registered under the subordinate key "chest_inventories".

Every inventory also has its own unique inventory identifier that is used throughout the rest of the configuration. The inventory identifier is the third upper-most key (right after the inventory type) and consists of a sequence of characters.

#### Inventory example
```yml
inventories:
  chest_inventories:
    ultrachest_menu:  # ultrachest_menu is the inventory identifier
      title: "Ultrachest Menu"
      size: 36  # The inventory will have 36 cells
      content:
        0:  # The first cell will contain a beacon_block ItemStack
          item: beacon_block  # beacon_block is an item identifier
          actions:  
            REPLACE_ITEM: # It will have a click action to replace item
              ITEM: slime_block # slime_block is an item identifier of the new item
            # Once this inventory cell is clicked,
            # it will replace its content with a slime_block ItemStack
```

### Properties
There is a number of supported inventory properties. All of these properties are mandatory.

| Property | Value type |
|----------|----------|
|title|String with color formatting|
|size|Number|
|content|*See below for an explanation*|

#### Title
Specifies the title of the inventory that will be displayed in the upper-left corner of the inventory. It supports color formatting using the "paragraph" **§** symbol.

#### Size
Specifies the number of cells in the inventory.

#### Content
```yml
content:
  ITEM_SLOT_NUMBER: # The slots are numbered starting with 0!
    item: ITEM_IDENTIFIER # Item identifier of the item that will be in this cell
    actions:  # All click actions of the given inventory slot
      CLICK_ACTION_NAME_1:  # Every action has its unique name
        ACTION_ARGUMENT_NAME_11: ARGUMENT_DATA_11 # Every action may also have its arguments
        ACTION_ARGUMENT_NAME_12: ARGUMENT_DATA_12
      CLICK_ACTION_NAME_2:
        ACTION_ARGUMENT_NAME_21: ARGUMENT_DATA_21

```
The content of the inventory and all inventory click actions are specified in this part as subordinate keys and values. The main key represents the slot number of the ItemStack (starting from 0). The actual item content of the inventory slot is specified in a subordinate (key: value) pair, where the key is "item" and the value is an item identifier of an item registered in the item section of the configuration.

The inventory click actions manage the logic behind the inventory GUIs. Every CommonCore plugin has custom and unique inventory click actions built to serve its functionality. Most of the inventory click actions also have action arguments that need to be defined. Please, visit plugin-specific documents to learn about the inventory click actions supported by your plugin.

All inventory click actions for the specified inventory slot are defined by their action name under the subordinate key "actions". Every action name also serves as a key and it may contain other (key: value) pairs representing the click action arguments. These click action argument (key: value) pairs specify the argument name for the given action as a key and the argument value as the value of the (key: value) pair.

### Inventory examples
These inventory examples represent inventories containing the ItemStacks that were shown as item examples. Please bear in mind that some of the inventory click actions used throughout these examples may not be supported by all CommonCore plugins.

#### Example 1
Definition in the configuration file:
```yml
inventories:
  chest_inventories:
    ultrachest_menu:  # ultrachest_menu is the unique inventory identifier
      title: "§5§lUltraChest §7Menu"
      size: 36
      content:
        0:
          item: beacon_block  # beacon_block is an item identifier
          actions:
            REPLACE_ITEM:
              ITEM: slime_block # slime_block is an item identifier
        3:
          item: slime_block # slime_block is an item identifier
          actions:
            PLAY_SOUND:
              NAME: FIRE_IGNITE
```
Will result into:

![Example 1](https://ctrlv.sk/shots/2020/03/04/fcqp.png)

where clicking on the beacon block will replace the beacon block with 12 slime blocks and clicking on the slime blocks in the 4th inventory slot will play a sound called FIRE_IGNITE.

#### Example 2
Definition in the configuration file:
```yml
inventories:
  chest_inventories:
    main_menu:  # main_menu is the unique inventory identifier
      title: "Better Beacons"
      size: 45
      content:
        11:
          item: beacon_block # beacon_block is an item identifier
          actions:
            OPEN_INVENTORY:
              INVENTORY_NAME: ultrachest_menu
```
Will result into:

![Example 2](https://ctrlv.sk/shots/2020/03/04/D0h1.png)

where clicking on the beacon block will open the inventory specified in the example 1.

### Video tutorial
<div style={{position: "relative", paddingBottom: "56.25%", paddingTop: "25px", height: 0}}>
<iframe style={{position: "absolute", top: "0", left: "0", width: "100%", height: "100%"}} src="https://www.youtube.com/embed/_8Fs3UMWVTE" frameborder="0" allow="accelerometer; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
</div>

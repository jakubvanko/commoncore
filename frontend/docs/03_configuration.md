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
<iframe width="560" height="315" src="https://www.youtube.com/embed/ZbmzYmBAteQ" frameborder="0" allow="accelerometer; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

## Items
All items used in the given CommonCore plugin have to be specified in the item section of the configuration. Every item has its own unique item identifier that is used throught the rest of the configuration. The item identifier is the upper-most key and consists of a sequence of characters.

Once the item identifier has been specified, you can optionally define other properties of the ItemStack. The property "material" is the only mandatory property.

### Properties
There is a number of supported item properties.

| Property | Value type |
|----------|----------|
|material|Bukkit MATERIAL|
|name|String with color formatting|
|amount|Number|
|damage|Number (if damageable)|
|unbreakable|true/false|
|lore|Array of color formatted strings|
|item_flags|Array of Bukkit ITEM_FLAGS|
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
Specifies the amount of the items in the ItemStack. If an ItemStack with an amount higher than 1 is used in other parts of the plugin, it may cause the plugin to work properly only if the player uses the exact amount of items in the exact same ItemStack.

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
    lore: # An array of lore strings
      - "Use this tool"     # First lore line
      - "on a beacon to"    # Second lore line
      - "interact with it!" # Third lore line
```
Specifies the lore of the ItemStack as an array of strings, where each line corresponds to one lore line. It also supports color formatting using the "paragraph" **§** symbol.

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
    item_flags: # An array of item flags
      - HIDE_ENCHANTS       # The enchantments of the item will be hidden
```
Specifies the item flags of the ItemStack as an array of Bukkit item flags. Make sure that your Minecraft server version supports the item flags that you want to use. Using item flags on old Minecraft versions will break the plugin.

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
TODO

### Video tutorial
<iframe width="560" height="315" src="https://www.youtube.com/embed/K4JprGvXQMY" frameborder="0" allow="accelerometer; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

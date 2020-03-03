---
id: 02_yaml
title: YAML
sidebar_label: YAML
---

YAML is a human-readable data-serialization language used to specify configurations of the CommonCore plugins. It has a very simple structure and can be easily understood even by non-programmers. However, some CommonCore plugins may sometimes use more advanced features of YAML that are described in this document. For a more complex YAML tutorial, please visit [this link](https://www.tutorialspoint.com/yaml/index.htm).

## Multi-line strings
Some plugin messages may consist of multiple lines of text. Multi-line strings allow you to define such messages in a very simple manner.
Multi-line strings are specified using the "vertical bar" character **|**.

### Normal string example
Normal string are in YAML specified in the following way:
```yml
STRING_ID: "Text that you want to specify"
```

### Multi-line string example
Multi-line strings have the following structure:
```yml
STRING_ID: |
    Text that you want to specify on the first line
    Second line of text
    Another line of text
```

## Node anchors and references
Sometimes there is a need to define multiple YAML keys where the subordinate (key: value) pairs are almost the same. The simple and non-productive way to do this would be to simply copy the pair as many times as needed and apply the needed changes to every copy. This method can, however, create configuration files that span hundreds of lines. Node anchors and references are a solution to this problem.

First of all, a named node anchor needs to be created to specify the YAML (key: value) pair that should be used throughout the configuration file. Once that is done, a reference to the anchor can be created anywhere inside of your configuration. The reference will implicitly copy all subordinate values and (key: value) pairs. If you want to change a value, you can do so by explicitly specifying it in the (key: value) pair.

Node anchors are defined using the "and" **&** symbol coupled with a string specifying the name of the anchor. Node references are defined using a "star" **\*** symbol coupled with the name of the anchor the reference should point to. You can also use two "less-than" **<<** symbols to optionally specify the place of the insertion. The "less-than" symbols are used if the copied value contains also other subordinate (key: value) pairs.

### Node anchor definition
```yml
ID1: &anchor_name
    KEY1: value1
    KEY2: value2
    KEY3: # Keys may also have values representing arrays
      - value3
```

### Node reference definition
```yml
ID2:
    <<: *anchor_name  # Will copy all (key: value) pairs from ID1
    KEY2: value5  # Value of KEY2 will be overwritten
    KEY4: value4  # A new (key: value) pair will be added
```

### Complete examples
#### Example 1
Definition in the configuration file:
```yml
ID1: &anchor_name
    KEY1: value1
    KEY2: value2
    KEY3: # Keys may also have values representing arrays
      - value3
ID2:
    <<: *anchor_name  # Will copy all (key: value) pairs from ID1
    KEY2: value5  # Value of KEY2 will be overwritten
    KEY4: value4  # A new (key: value) pair will be added
```
Will implicitly translate into:
```yml
ID1:
    KEY1: value1
    KEY2: value2
    KEY3: # Keys may also have values representing arrays
      - value3
ID2:
    KEY1: value1  # KEY1 will be copied along with its value
    KEY2: value5  # Value of KEY2 will be overwritten
    KEY3: # KEY3 will be copied also along with its value
      - value3
    KEY4: value4  # A new (key: value) pair will be added
```

#### Example 2
Definition in the configuration file:
```yml
items:
    slime_block:
        name: "Jump Boost"
        material: "SLIME_BLOCK"
        lore: &default_lore # Anchor definition
          - "What a nice item!"
    sugar:
        name: "Speed"
        material: "SUGAR"
        lore: *default_lore # Direct insertion of default lore value into the key
```
Will implicitly translate into:
```yml
items:
    slime_block:
        name: "Jump Boost"
        material: "SLIME_BLOCK"
        lore:
          - "What a nice item!"
    sugar:
        name: "Speed"
        material: "SUGAR"
        lore: # The value of the item lore is copied
          - "What a nice item!"
```

## Video tutorial
<div style={{position: "relative", paddingBottom: "56.25%", paddingTop: "25px", height: 0}}>
<iframe style={{position: "absolute", top: "0", left: "0", width: "100%", height: "100%"}} src="https://www.youtube.com/embed/gUPoq7Z0NRw" frameborder="0" allow="accelerometer; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
</div>

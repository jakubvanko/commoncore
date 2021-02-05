# **CommonCore**

This repository contains the CommonCore library and plugins as well as the website for CommonCore plugins. You can visit the website at [commoncore.jakubvanko.com](https://commoncore.jakubvanko.com).

#### Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Development](#development)
- [Documentation](#documentation)
- [CommonCore Plugins](#plugins)

## Introduction

CommonCore is a Bukkit library that provides Minecraft plugins with the most useful functionalities. All plugins developed using CommonCore have a similar configuration structure.

The most important principles of CommonCore plugins are complete flexibility and customizability. Server administrators can use the configuration of CommonCore plugins to specify or change almost every functionality provided by CommonCore plugins.

CommonCore also comes bundled with [XSeries](https://github.com/CryptoMorin/XSeries) that provides developers with additional utilities to simplify plugin creation.


## Features

- Material Convertor
  - Ensures cross-version material compatibility (1.8 - 1.15)
  - Administered by [XSeries](https://github.com/CryptoMorin/XSeries)
- Sound Convertor
  - Ensures cross-version sound compatibility (1.8 - 1.15)
  - Administered by [XSeries](https://github.com/CryptoMorin/XSeries)
- Item Builder
  - Implements Builder design pattern to simplify the creation of ItemStacks
- Config Loader
  - Loads ItemStacks, Recipes and Inventories directly from the configuration
- Message Manager
  - Creates adaptable strings (abbreviations) and simplifies the process of messaging players
- Vault Manager
  - Integrates and simplifies the loading and interaction of the Vault plugin API
- Inventory GUI Simplification
  - Implements an action-like inventory GUI structure
- Complete Documentation
  - Includes a complete documentation for programmers and also users of CommonCore plugins
- [XSeries](https://github.com/CryptoMorin/XSeries)
  - Includes [XSeries](https://github.com/CryptoMorin/XSeries) to provide additional utilities for your plugin
- BStats
  - Includes BStats to provide metrics for your plugin
- Custom Items and Textures Support
  - Implements experimental support for the 3rd party Custom Items and Textures plugin.


## Installation

To use CommonCore library in a plugin, the library's .jar file needs to be downloaded and set as a dependency for the given project. It is also vitally important to include the compressed CommonCore code in the final plugin's .jar file.


## Development

The development of CommonCore needs the following dependencies located in the folder named "__external":

- XSeries - must be  **EXTRACTED** into the compiled artifact
- Vault - **NOT to be included** in the compiled artifact
- Spigot API at least 1.13.2 - **NOT to be included** in the compiled artifact

Plugins developed using the CommonCore library must extract the CommonCore library into the compiled artifact of the plugin.

## Documentation

Documentation and tutorials for programmers and server administrators are to be found on the [website of the CommonCore library](https://commoncore.jakubvanko.com).


## Plugins
Plugins currently using the CommonCore library:
- [**BetterBeacons - The most advanced beacon plugin in the universe!**](https://dev.bukkit.org/projects/better-beacons "**BetterBeacons - The most advanced beacon plugin in the universe!**")
- [**UltraChest - An interactive reward!**](https://dev.bukkit.org/projects/ultra-chest-interactive-reward "UltraChest - An interactive reward!")

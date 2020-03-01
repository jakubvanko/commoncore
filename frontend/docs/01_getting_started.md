---
id: 01_getting_started
title: Getting Started
sidebar_label: Getting Started
---

## Introduction

CommonCore is a Bukkit library that provides Minecraft plugins with the most useful functionalities. All plugins developed using CommonCore have a similar config structure. This document is targeted at server administrators that want to understand and customize their CommonCore plugins.

As a server administrator, you do not have to install CommonCore library separately as it comes automatically bundled with every CommonCore plugin.
If you are a developer interested in developing plugins using CommonCore library, please visit the document titled CommonCore API Reference.

## Design principles
The most important principles of CommonCore plugins are complete flexibility and customizability. Server administrators can use the configuration of CommonCore plugins to specify or change almost every functionality provided by CommonCore plugins.

### Hyperlinks
The configuration resembles a hyperlink structure where every definition (of an ItemStack, Inventory etc.) has its unique identfier. The identifier is represented by the key in (key: value) pairs. This identifier is than used in other definitons throught the configuration file. For example, an identifier of a defined ItemStack can be later used in the definiton of an Inventory. This ensures that every change will be consistently applied to every possible context.

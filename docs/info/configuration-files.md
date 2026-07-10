---
description: >-
  The plugin generates the following configuration files, some of which will
  only be generated after you first use this feature.
---

# 🛠️Configuration files

* `items`: The location for storing saved item files. It will only be generated after save any item with `/mc saveitem` command. <mark style="color:red;">Do not modify any content here</mark>.
* `languages`: The location for storing language files. You can set the language file used by the plugin through the `config-files.language` option in the `config.yml` file. You can customize various messages within the plugin game through language files. It is not supported to display the corresponding language file based on the player client language. You can only display the same language for all players.
* `rules`: The location for storing **Auto Match & Change** feature's configuration files.
* `apply_items`: The location for storing **Apply Item** configuration files. **Drag Change / GUI Change** feature requires you consume Apply Item to apply item change onto specifed items.
* `config.yml` file: The location for main common settings for plugins.
* `generated-item-format.yml` file: When using the `/mc generateeitemformat` command, we will parse the item you are holding into an **ItemFormat** and store the parsed **ItemFormat** content in this file.
* `XX_xx.json` file: Localized files automatically generated through [Localized Item Name](../features/localized-item-name-premium.md) feature. The name of this file is determined based on the localized language you have set for this feature, but it usually ends in `. json`.

## Config.yml file content

It is recommend that you view this file at GitHub, becuase Wiki's `config.yml` maybe not **latest**. Click [here](https://github.com/ManyouTeam/MythicChanger/blob/master/src/main/resources/config.yml) to view this file on **Github.**

```yaml
# MythicChanger by @PQguanfang
#
# Read the Wiki here: https://mythicchanger.superiormc.cn/

# Some options require restart the server to make effect.

debug: false

# If you just want to use our match item feature without any need of change item,
# you can disable this option to improve plugin performance and disable auto item change feature.
packet-listener: true

# packetevents Support value:
#    LOWEST(0),
#    LOW(1),
#    NORMAL(2),
#    HIGH(3),
#    HIGHEST(4),
#    MONITOR(5)
# The value after the value just a number help you know it's level, do not type them in option.
packet-listener-priority: LOWEST

config-files:
  language: en_US
  # Premium version only.
  per-player-language: true
  force-parse-mini-message: true
  # Premium version only.
  minecraft-locate-file:
    # After enable, we will autoload Minecraft locate file when we need know an item's locate name.
    # It will make server little lag when loading this file because this file is very large.
    enabled: true
    generate-new-one: false
    file: 'zh_cn.json'

debuild-item-method: 'LEGACY'

# Support value: DEFAULT or ITEMBRIDGE
hook-item-method: 'DEFAULT'

# Premium version only.
math:
  enabled: true
  scale: 2

real-change-trigger:
  SetSlotPacket:
    enabled: true
  # Require Paper.
  # A better choice to replace "SetSlotPacket"
  PlayerInventorySlotChangeEvent:
    enabled: false
  InventoryClickEvent:
    enabled: true

fake-change-trigger:
  # Normal triggers like SetSlotsPacket, WindowsItemPackets, SetCursorItemPacket so on are force enabled.
  # Entity Packets means we will also modify item from other entities which is very cost server performance if you enable the two options here.
  EntityEquipmentPacket:
    enabled: false
  EntityMetadataPacket:
    enabled: false

ignore-warning: false

# Whether we keep item name to original in anvil name.
keep-name-in-anvil: true

# Whether only-in-player-inventory option in each rule only checks chest UI.
check-chests-only: true

ignore-player-crafting-slot: false

# Premium version only.
change-gui:
  title: '{lang:change_gui.title}'
  size: 27
  ignore-click-outside: false
  anti-dupe-checker: false
  item-slot: 11
  book-slot: 15
  confirm-slot: 22

  confirm-item:
    material: ANVIL
    name: '{lang:change_gui.confirm.name}'
    lore:
      - '{lang:change_gui.confirm.lore}'

  custom-item:
    2:
      material: PAPER
      name: '{lang:change_gui.custom.item}'
    6:
      material: BOOK
      name: '{lang:change_gui.custom.book}'

  close-if-fail: true

# MY_CUSTOM:
#   title: 'Change your item!'
#   size: 27
#   item-slot: 11
#   book-slot: 15
#   confirm-slot: 22
#   confirm-item:
#     material: ANVIL
#     name: '&eConfirm'
#     lore:
#       - '&7Click to confirm change the item!'
#   custom-item:
#     2:
#       material: PAPER
#       name: '&bDrag the item you want change below.'
#     6:
#       material: BOOK
#       name: '&bDrag the apply item below.'
#   close-if-fail: true

apply-item-mode:
  drag-enabled: true
  # GUI mode is in EARLY ALPHA TEST, be careful to use!
  # Premium version only.
  gui-enabled: true

default-apply-item-limit: 1
```

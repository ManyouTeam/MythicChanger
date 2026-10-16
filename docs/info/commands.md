# ⌨️Commands

## /mc generateitemformat&#x20;

Save the item held in your main hand to `plugins/MythicChanger/generated-item-format.yml`. Each use overwrites that file. Set `debuild-item-method: COMPONENT` to use the component writer on Paper 1.21.5+ with MythicChanger Premium. See [Testing Paper Components](../format/component-testing.md) for a save/rebuild walkthrough.

Require `mythicchanger.generateitemformat` permission.

## /mc giveapplyitem \<itemID> \<playerID> \<amount>&#x20;

Give [Apply item](../drag-gui-change/apply-item.md) to players.

Require `mythicchanger.giveapplyitem` permission.

## /mc help

View plugin help.

## /mc reload

Reload the plugin. Some changes still need restart the whole server.

Require `mythicchanger.reload` permission.

## /mc saveitem \<itemID> \[bukkit|itemformat]

Save your held item. `bukkit` is the default save method; `itemformat` follows `debuild-item-method`. For example, `/mc saveitem component-test itemformat` saves the ItemFormat under `items/component-test.yml`.

Require `mythicchanger.saveitem` permission.

## /mc givesaveitem \<itemID> \[player] \[amount]

Give saved item to players.

Require `mythicchanger.givesaveitem` permission.

## /mc viewnbt <mark style="color:red;">**(Premium version only)**</mark>

View hold item NBT info, it will only display the plugin supported NBT Type.

Require NBTAPI plugin.

Require `mythicchanger.viewnbt` permission.

## /mc openchangegui <mark style="color:red;">**(Premium version only)**</mark>

Open mythic change GUI, require you enable `apply-item-mode.gui-enabled` to **true** in `config.yml`. (This option require server restart)

Require `mythicchanger.openchangegui` permission.

## /mc openchangegui \<customModeName> <mark style="color:red;">**(Premium version only)**</mark>

If you have set a custom apply item mode for certain apply items, then players must use this command to enter the GUI where they can use this apply item.

Require `mythicchanger.openchangegui` permission and `mythicchanger.openchangegui.<customModeName>` permission.

## /mc setlimit \<amount> <mark style="color:red;">**(Premium version only)**</mark>

Set hold item's apply limit.

Require `mythicchanger.setlimit` permission.

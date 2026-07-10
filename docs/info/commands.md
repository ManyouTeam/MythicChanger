# ⌨️Commands

## /mc generateitemformat&#x20;

Generate hold item into Item Format at `plugins/MythicChanger` folder.

Require `mythicchanger.generateitemformat` permission.

## /mc giveapplyitem \<itemID> \<playerID> \<amount>&#x20;

Give [Apply item](../drag-gui-change/apply-item.md) to players.

Require `mythicchanger.giveapplyitem` permission.

## /mc help

View plugin help.

## /mc reload

Reload the plugin. Some changes still need restart the whole server.

Require `mythicchanger.reload` permission.

## /mc saveitem \<itemID>

Save your hold items.

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

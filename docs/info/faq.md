# ❓FAQ

## Seems that my rule configs are correct, why my items does not change?

* MythicChanger won't modify creative mode players item, you have to change your game mode to survival or adventure.
* For real change: The modification is not immediate and requires players to interact with the item, such as droping it to the ground and picking it up again.
* Maybe your ProtolcolLib plugin is not correctly loaded or it is not latest (download latest at it's Jenkins!).

## Seems that when I click the item, it will disappear or dupe.

* Update MythicChanger to 1.3.2 and higher version maybe solve your problem.
* Try disable `real-change-trigger.InventoryClickEvent.enabled` option in `config.yml` then restart the server.

## Why I can not click a item?

* That maybe because you use `replace-item` change rule, and after replace the item, the new item still meet the match rule, so this has fallen into a dead cycle.

## This plugin crashs my server!

* This is likely due to your item replacement getting stuck in a dead loop. If the replaced item still meets the conditions of other rules, it will be continuously replaced. Please ensure that the replaced item will not meet any other rules.

# ⛔Limitation

## Creative Mode

The **fake change feature** of this plugin will not have any effect on **creative modes**.

## Compatibility of other packet based item plugins

Some other item plugins and enchantment plugins **MAY** are also using packet to change items, in which case MythicChanger's **fake change** may not work on items from these plugins. MythicChanger provides a free version for testing plugin compatibility.

## Real change is not instantaneous

This plugin does not instantly apply **real changes**. We will only attempt to make real changes to the item after the player interacts with it. In survival mode, the vast majority of operations that can obtain items will be recognized by the plugin and successfully perform real changes. So this limitation can almost be ignored.

## Real change will only occur within the player's inventory

In order to prevent abnormal situations such as **item duplication**, **real change** will only be performed within the player's inventory, and items in the "treasure chest" and "vault" will not undergo real change. Due to this issue, it is highly recommended that you use both real change and fake change if you are using change rules like `replace-item`.

```yaml
fake-changes:
  replace-item:
    hook-plugin: MMOItems
    hook-item: 'MATERIAL;;REAR_INGOT'
real-changes:
  replace-item:
    hook-plugin: MMOItems
    hook-item: 'MATERIAL;;REAR_INGOT'
```

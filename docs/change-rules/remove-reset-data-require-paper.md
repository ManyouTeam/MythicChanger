# Remove/Reset Data - Require Paper

For list of available data type, please view: [https://jd.papermc.io/paper/26.1.2/io/papermc/paper/registry/keys/DataComponentTypeKeys.html](https://jd.papermc.io/paper/26.1.2/io/papermc/paper/registry/keys/DataComponentTypeKeys.html)

## Remove Data

Remove the specified data type in item.

This also includes the most basic data values under vanilla items.

Maybe very hard to understand, right?

Let us take an example:

By default, diamond pickaxe contains a tool data that allows it to mine stones faster. If we delete it, the speed of diamond pickaxe mining stones will be the same as its empty hand speed.

```yaml
match-item:
  items:
    - 'diamond_pickaxe'
    
real-changes:
  remove-data:
    - 'tool'
```

## Reset Data

Unlike Remove Data, Reset Data only restores the data corresponding to the item to its default value, rather than completely deleting it.

```yaml
real-changes:
  reset-data:
    - 'tool'
```

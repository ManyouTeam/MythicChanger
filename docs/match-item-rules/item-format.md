# Item Format

## Item Format

Require item's item format specifed key's value must same as the value set here.

```yaml
match-item:
  item-format:
    material: ENCHANTED_BOOK
    stored-enchants:
      bane_of_arthropods: 5
  item-format-settings:
    require-same-key: false
    ignore-key:
      - 'amount'
```

You can hold the item and use command `/mc generateitemformat` to generate item format at **the plugin (plugins/MythicChanger)** folder.

Options for `item-format-settings`:

* require-same-key: This means that the items set here must have all the data of the items owned by the player.
* ignore-key: The list of ItemFormat™ Key that will be ignored when check whether items are same.

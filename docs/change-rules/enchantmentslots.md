# EnchantmentSlots

## Set Slot <a href="#set-slot" id="set-slot"></a>

```yaml
real-changes:
  # Automatically calculate the slot of items
  es-set-slot: true
  # Manually set the slot
  es-set-slot: 5
```

## Add Slot <a href="#reset-slot" id="reset-slot"></a>

```yaml
real-changes:
  es-add-slot: -5
```

## Reset Slot <a href="#reset-slot" id="reset-slot"></a>

Reset means we will override the slot already exist in the item.

```yaml
real-changes:
  # Automatically calculate the slot of items
  es-reset-slot: true
  # Manually reset the slot
  es-reset-slot: 5
```

## Add Enchantment Slot Lore <a href="#add-enchantment-slot-lore" id="add-enchantment-slot-lore"></a>

```yaml
fake-changes: 
  es-add-lore: true
```

## Parse Item Placeholder from ES

For more info about Item Placeholder, please view [here](https://enchantmentslots.superiormc.cn/general-configs/built-in-placeholders).

```yaml
fake-changes: 
  es-parse-lore: true
```

## Remove Excess Enchantments

```yaml
real-changes:
  es-remove-excess-enchants: true
```

## Remove Enchantment Slot Lore

```yaml
real-changes:
  es-remove-lore: true
```

## Has Slot

Does the slot is more than specifed value?

```yaml
match-item:
  has-slot: 5
```

# Add enchants

## Add enchants <a href="#add-enchants" id="add-enchants"></a>

Add enchantments to items. Please note that in general, you should use it in `real-changes`. If you use it in `fake-changes`, the enchantment will actually take effect, but it appears to disappear.

```yaml
real-changes:
  add-enchants:
    POWER: 1
  add-enchants-ignore-level: true # If set to false, and the item already has this enchantment, we will check the enchantment level item has, if greater than the value you set here, we will skip this rule.
```

## Add stored enchants

```yaml
real-changes:
  add-stored-enchants:
    POWER: 1
  add-stored-enchants-ignore-level: true # If set to false, and the item already has this enchantment, we will check the enchantment level item has, if greater than the value you set here, we will skip this rule.
```

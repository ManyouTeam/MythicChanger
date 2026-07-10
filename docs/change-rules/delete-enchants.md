# Delete enchants

## Delete enchants <a href="#delete-enchants" id="delete-enchants"></a>

Delete the enchantment on the item, please note that the number after the enchantment represents the effective value. If the enchantment level of an item is greater than this value (excluding this value), the enchantment will be deleted.

In this example, items with Knockback I enchantment will not be affected, while items with Knockback II enchantment will be deleted.

```yaml
real-changes:
  delete-enchants:
    KNOCKBACK: 1
```

If you want to make only specified enchantment level take effect, you can use int list at here. For example, I want to make only item has Knockback level 1 enchantment match, then you should set this section like this:

```yaml
real-changes:
  delete-enchants:
    KNOCKBACK: [1] # Added = symbol before the value.
    OTHERS: [1,2,5] # Means level1, 2 and 5.
```

## Delete stored enchants

```yaml
real-changes:
  delete-stored-enchants:
    KNOCKBACK: 1
```

```yaml
real-changes:
  delete-stored-enchants:
    KNOCKBACK: [1] # Added = symbol before the value.
    OTHERS: [1,2,5] # Means level1, 2 and 5.
```

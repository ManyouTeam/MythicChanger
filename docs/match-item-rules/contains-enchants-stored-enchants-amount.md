# Contains Enchants/Stored Enchants Amount

## Min Enchants Amount <mark style="color:red;">- Premium</mark>

Check enchantment count.

The item must has at least 5 enchantments in this example.

```yaml
match-item:
  contains-enchants-amount: 5
```

## Contains Enchants Amount <mark style="color:red;">- Premium</mark>

Check enchantment count list.

This means item must has 3 or 5 enchantments in this example, if the item has 4 enchantments or other number of enchantments, it will not being matched.

```yaml
match-item:
  contains-enchants-amount: [3, 5]
```

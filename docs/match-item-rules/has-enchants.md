# Has Enchants

## Has Any Enchants <mark style="color:red;">- Premium</mark>

Require the item to has any enchantment.

```yaml
match-item:
  has-enchants:
    - '*'
```

## Has Enchants

Require the item to contain any listed enchantment.

```yaml
match-item:
  has-enchants:
    - 'POWER'
```

## Has Stored Enchants

For enchanted books, require stored enchantments.

* only valid for stored-enchant metadata
* `*` means any stored enchantment

```yaml
match-item:
  has-stored-enchants:
    - 'POWER'
```

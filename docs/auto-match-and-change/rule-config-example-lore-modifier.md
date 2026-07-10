# 📚Rule Config Example - Lore Modifier

## Config

```yaml
weight: 15

# If you want to all item apply this rule, just delete whole match_item section.
match-item:
  items:
   - 'diamond_sword'

fake-changes:
  set-name: '&fGood Diamond Sword'
  set-lore:
    - '&7A good sword that all adventures should use!'
    - '&f&lCommon'
  add-lore-prefix: '1'

real-changes:
  add-enchants:
    KNOCKBACK: 1
  remove-enchants:
    - 'POWER'
```

## In-game showcase

<figure><img src="../.gitbook/assets/image (1).png" alt=""><figcaption></figcaption></figure>

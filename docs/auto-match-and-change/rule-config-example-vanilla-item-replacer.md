# 📚Rule Config Example - Vanilla Item Replacer

## Config

```yaml
weight: 15

# If you want to all item apply this rule, just delete whole match_item section.
match-item:
  items:
   - 'iron_sword'
  # Whether you use item tier instead of item ID to identify item slots.
  # If this item plugin don't have tier, will still use Item ID as identify.
  use-tier-identify: false

fake-changes:
  replace-item:
    hook-plugin: MMOItems
    hook-item: WAND;;OAK_WAND
    keep-enchants: true

real-changes:
  replace-item:
    hook-plugin: MMOItems
    hook-item: WAND;;OAK_WAND
    keep-enchants: true
```

## In-game showcase

<figure><img src="../.gitbook/assets/image (2).png" alt=""><figcaption></figcaption></figure>

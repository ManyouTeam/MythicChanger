# Items

{% hint style="info" %}
It is recommend that you use [material](material.md) or [item-format](item-format.md) match rule instead, this rule is not very good for use.
{% endhint %}

## Items

Match by external plugin item ID.

```yaml
match-item:
  # Type item ID here, support third item plugins item ID.
  items:
    - 'diamond_sword'
    - 'superior_sword'
  use-tier-identify: true
```

{% hint style="info" %}
Type item ID or material ID **only**, no item type.

For MMOItems: Please note that the item ID needs to be capitalized.

For ItemsAdder: Namespace is required, like `namespace_test:item_id_here`.
{% endhint %}

## Use tier identify

For MMOItems and EcoItems plugin, there is also a `use-tier-identity` option available, which represents whether to use item ID or item tier. If changed to true, you can use tier IDs such as **RARE** here instead of the previous item ID.

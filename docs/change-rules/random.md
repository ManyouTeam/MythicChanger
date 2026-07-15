# Random

{% hint style="info" %}
Using random type rules for **fake change** is <mark style="color:red;">**meaningless**</mark> because it will cause the item to constantly change (because each time player interact item, we will start fake change). Suggest using them only in **real changes**.\
Even using in **real change**, you need make sure **after random change, the item after change should NOT match the same rule again** otherwise the item will still constantly change because it still match the rule and randomly changed.

Example:

```yaml
match-item:
  items:
    - 'diamond'
  has-lore: false # This match rule make sure after change, the item will no longer be random changed by this rule.

real-changes:
  random-change:
    1:
      set-lore: 
        - 'Lucky Boy' # After change, the item will has lore, so it will only be changed once, otherwise it will always be changed by this rule.
      rate: 5
    2:
      set-lore: 
        - 'Not Lucky Boy'
      rate: 15
```
{% endhint %}

## Random Replace Item\* <mark style="color:red;">- Premium</mark>

Use the ItemFormat [here](https://ultimateshop.superiormc.cn/base/item-format).

```yaml
real-changes:
  replace-random-item:
    1:
      # Put Item Format here.
      material: APPLE
      rate: 5
    2:
      # Put Item Format here.
      material: DIAMOND
      rate: 15
```

## Random Change <mark style="color:red;">- Premium</mark>

```yaml
real-changes:
  random-change:
    1:
      changes:
      # Put Change Rules here.
        set-lore: 
          - 'Lucky Boy'
      match-item: []
      rate: 5
      actions:
        1:
          type: message
          message: 'So lucky!'
    2:
      changes:
        set-lore: 
          - 'Not Lucky Boy'
      rate: 15
```

# 📌Rule

Rule determines which items need to be modified and what they look like are determined by each rule in Auto Match\&Change feature.

## Rule Configs

All rules are existed at `rules` folder, you can copy `default.yml` to rename it to other words to create a new rule.

```yaml
weight: 15

only-in-player-inventory: true

# If you want to all item apply this rule, just delete whole match_item section.
match-item:
  items:
    - 'diamond_sword'
  # Whether you use item tier instead of item ID to identify item slots.
  # If this item plugin don't have tier, will still use Item ID as identify.
  #use-tier-identify: true
  #has-name: true
  #has-lore: true
  #contains-lore:
  #  - 'test'
  #contains-name:
  #  - 'test'

fake-changes:
  add-name-first: '&fGood '
  #set-name: '&fGood Diamond Sword'
  #add-lore:
  #  - '&f&lCommon'
  #set-lore:
  #  - '&7A good sword that all adventures should use!'
  #  - '&f&lCommon'
  #add-lore-prefix:
  #  - '%img_tooltip1%'
  #set-custom-model-data: 5
  #add-flags:
  #  - 'HIDE_ENCHANTS'

real-changes:
  add-enchants:
    KNOCKBACK: 1
  #remove-enchants:
  #  - 'POWER'
  #remove-all-enchants: true
  #delete-enchants:
  #  POWER: 3
  #replace-item:
  #  material: golden_sword
  
real-change-actions:
  1:
    type: message
    message: '&#98FB98[MythicChanger] &fChanged item!'

conditions:
  1:
    type: permission
    permission: 'mythicchanger.admin'
```

## General Options

* `weight` option represents this rule apply weight.
* `match-item` option represents which items will trigger this rule.
* `fake-changes` option represents what content will be modified at the client level.&#x20;
* `real-changes` option represents what content will be modified both in the client and server.
* `real-change-actions` option represents the action will executed when we applying real changes. Use **Action Format** here, for more info, please view [Format](../format/action-format.md) page. <mark style="color:red;">**PREMIUM VERSION ONLY.**</mark>
* `conditions` option represents players must meet those conditions to apply this rule. Use **Condition Format** here, for more info, please view [Format](../format/action-format.md) page.  <mark style="color:red;">**PREMIUM VERSION ONLY.**</mark>
* If you don't want to make fake or real change, you need delete the whole section instead of put empty value there!

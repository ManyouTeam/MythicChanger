# 🪄Apply Item

For 2.1.0+: <mark style="color:red;">**Free version can only create up to 3 apply items, please consider buy the premium version if you want to more.**</mark>

## Apply item configs

All apply items are saved in `apply_items` folder, an example file is here:

```yaml
apply-item-mode: GENERAL

# Display Item
display-item:
  material: PAPER
  name: '&#da8f87Attribute Scroll &7(Attack Damage &a+0.5&7)'
  lore:
    - '&8Scroll'
    - ''
    - '&7Drag any &cswords or axes &7onto this item to'
    - '&7make its &bAttack Damage &7value &a+0.5&7.'
    - '&7Up to value &410&7 can be upgraded.'
    - ''
    - '&bSuccess Rate: &a50%'
    - '&a&lRare'
  max-stack: 1
  
# Chance, Up to 100
chance: 50
  
# Rule Configs
fake-changes:
  set-custom-model-data: 50
real-changes:
  add-attributes:
    ATTACK_DAMAGE: 
      name: attack_damage 
      amount: '0.5'
      operation: ADD_NUMBER 
      slot: HAND 

# Apply Rule
apply-rule: example
apply-real-change: false

# Match Item
match-item:
  material-tag:
    - swords

# Conditions
conditions:
  1:
    type: placeholder
    rule: '<'
    placeholder: '{item_attributes.ATTACK_DAMAGE.amount;;0}'
    value: 10
    
# Actions
success-actions:
  1:
    type: message
    message: '&fCool, it looks like magic is showing off, your {name} &fis more powerful now!'
fail-actions:
  1:
    type: message
    message: '&fOh, no! Seems that this time you are now very lucky, the scroll broken, nothing changed!'
```

## General Options

We have those options for apply item!

* apply-item-mode: Please view [this page](apply-item-mode.md) to know more.
* display-item: The display item of this apply item. Should use [Item Format](https://ultimateshop.superiormc.cn/base/item-format) here.
* match-item: Read Match Rules page for more info, which item can use this apply item, if removed, all items can use this apply item.
* chance: The chance of the apply item be success to use. Can set a number from 0 to 100 here.
* success-actions/fail-actions: The success/fail actions will executed after player use this apply item. Use **Action Format** here, for more info, please view [Format](../format/action-format.md) page.
* conditions: The conditions player should meet to use the apply item. Use **Condition Format** here, for more info, please view [this page](../format/action-format.md).

## Apply item types and their specific options

There 2 types of apply items, different type of apply item have different specific options:

* **Rule Change:** Apply item based on [rules](../auto-match-and-change/rule.md). This means after use apply item, even this item do not meet match requirements of this rule configs, it will still be changed. Supports fake change and real change.
  * apply-rule: Which rule config will use after applied this item.
  * apply-real-change: Whether also apply rule's real changes.
* **Apply Change:** Apply item has it's own changes. This means after use apply item, we will change this item by the apply item config.
  * real-changes: Set the real change after use this apply item.
  * fake-changes: Set the fake change after use this apply item.

## Let the apply item only use the same item once <mark style="color:red;">- Premium</mark>

Start from 2.1.0, we won't prevent player use same apply item repeatly, if you want to this apply rule can only be used for each item once, you need add `contains-apply` rule at `match-item` section. Like:

```yaml
# <Other part of this apply item config>
match-item:
  not:
    contains-apply:
      - 'Write Apply Item ID here'
```

Please note that this only work for fake rule changes, if you are using real-changes, you need to figure out how to fill in the `match-item` option on your own to avoid duplicate matches. For example, if you set a `set-lore` rule, use the `contains-lore` matching rule, which is really very simple.

## Deapply <mark style="color:red;">- Premium</mark>

Want to rollback your modified item? This example apply item can help you!

```yaml
material: BOOK
name: '&fRollback book'
lore:
  - '&7Drag this item onto the item you want to rollback its apply.'
real-changes:
  deapply: true
match-item:
  has-apply: true
```

* You **MUST** set `apply-real-change` option to `false` in apply items config.
* This won't work for **REAL CHANGE**.

## Apply Limit <mark style="color:red;">- Premium</mark>

By default, each item can only use 1 apply item (not means use apply item once, for example, you used apply item A, other apply item B, C, etc can not use on this item, but A still can use) but, you can use new apply item system to avoid that.

* You can use `set-apply-limit`, `add-apply-limit`, `reset-apply-limit` change rules to edit item's apply item.
* Another way is use command `/mc setlimit <amount>` to edit your hold item's apply item. Then use `/mc saveitem <itemID>` command, after that, you can use `/mc givesaveitem <itemID>` command to give the item you edited.

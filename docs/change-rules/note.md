# ⚠️Note

You can use **ALL** change rules at both `fake-changes` and `real-changes` option except we told you can not do that in some specific rules.&#x20;

You need use [mi-update-lore](mmoitems.md) rule after you modify **MMOItems**'s nbt data.

The rule name end with an `*` symbol indicates that the item will be replaced after change, the original item will disappear, and then the new item will replace the original item. The data of the original item will be lost. You can consider using rules starting with `keep` to convert some of the data from the original item to the new item (this will cause some additional performance consumption). For more info, please view [here](keep.md).

When using **SubChange** and **RandomChange** rules, if a rule that requires a Replace Item is used, the parent rule will also be automatically considered as requiring a Replace Item.

<mark style="color:red;">**When using those rules, please be sure to note that the replaced item after the change should no longer meet this rule again, otherwise the item will be constantly replaced, causing a dead loop and ultimately causing the server to crash!**</mark><mark style="color:red;">**&#x20;**</mark>_<mark style="color:red;">**REMEMBER THIS!**</mark>_

```yaml
fake-changes:
  add-name-first: '&fGood '
  set-name: '&fGood Diamond Sword'
  add-lore-last:
    - '&f&lCommon'
  set-lore:
    - '&7A good sword that all adventures should use!'
    - '&f&lCommon'
  add-lore-prefix: '%img_tooltip1%'
  set-custom-model-data: 5
  add-flags:
    - 'HIDE_ENCHANTS'

real-changes:
  add-enchants:
    POWER: 1
  remove-enchants:
    - 'POWER'
  remove-all-enchants: true
  replace-item:
    material: golden_sword
```

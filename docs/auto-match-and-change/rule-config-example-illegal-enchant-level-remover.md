# 📚Rule Config Example - Illegal Enchant Level Remover

In this example, enchant level higher than 6 will be replaced to level 3.

{% hint style="info" %}
This example file will only work for <mark style="color:red;">**PREMIUM**</mark> version of **MythicChanger**.
{% endhint %}

## Config

```yaml
weight: 15

only-in-player-inventory: false

match-item:
  contains-enchants:
    UNBREAKING: 6

real-changes:
  add-enchants:
    UNBREAKING: 3
  add-enchants-ignore-level: true

real-change-actions:
  1:
    type: message
    message: '&#98FB98[MythicChanger] &fModified illegal enchantment level!'

conditions:
  1:
    type: permission
    permission: 'mythicchanger.admin'
```

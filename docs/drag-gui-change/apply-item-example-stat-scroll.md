# 📚Apply Item Example - Stat Scroll

{% hint style="info" %}
This example file will only work for <mark style="color:red;">**PREMIUM**</mark> version of **MythicChanger**.\
You <mark style="color:red;">**NEED**</mark> enable `bypass-real-change-limit` option at `config.yml` to make this example file work.

You <mark style="color:red;">**NEED**</mark> install **NBTAPI** plugin first.
{% endhint %}

```yaml
# Display Item
display-item:
  material: PAPER
  name: '&#da8f87Stat Scroll &7(Attack Damage &a+10%&7)'
  lore:
    - '&8Scroll'
    - ''
    - '&7Drag any &cswords or axes &7onto this item to'
    - '&7make its &bAttack Damage &7value &a+10%&7.'
    - '&7Up to value &420&7 can be upgraded.'
    - ''
    - '&bSuccess Rate: &a100%'
    - '&a&lRare'
  max-stack: 1
  
# Chance, Up to 100
chance: 100
  
# Rule Configs
real-changes:
  add-name-last: '&6★'
  add-nbt-double:
    - 'MMOITEMS_ATTACK_DAMAGE;;{nbt_MMOITEMS_ATTACK_DAMAGE;;1}*1.1'
  remove-nbt:
    - 'HSTRY_ATTACK_DAMAGE'
  mi-update-lore: true

# Match Item
match-item:
  items:
    - 'YourMMOItemsSwordID'
  not:
    contains-name: 
      - '&6★&6★&6★&6★&6★'

# Conditions
conditions:
  1:
    type: placeholder
    rule: '<'
    placeholder: '{nbt_MMOITEMS_ATTACK_DAMAGE;;1}'
    value: 18.18
    
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

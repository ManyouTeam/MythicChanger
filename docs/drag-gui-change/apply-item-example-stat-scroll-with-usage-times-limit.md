# 📚Apply Item Example - Stat Scroll with Usage Times Limit

{% hint style="info" %}
You <mark style="color:red;">**NEED**</mark> install **NBTAPI** plugin first.
{% endhint %}

```yaml
# Display Item
display-item:
  material: PAPER
  name: '&#da8f87Stat Scroll &7(Armor &a+10%&7)'
  lore:
    - '&8Scroll'
    - ''
    - '&7Drag any &carmor &7onto this item to'
    - '&7make its &bArmor &7value &a+10%&7.'
    - '&7Up to upgrade &420&7 times.'
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
    - 'MMOITEMS_ARMOR;;{nbt_MMOITEMS_ARMOR;;1}*1.1'
    - 'UPGRADE_TIMES;;{nbt_UPGRADE_TIMES;;0}+1' # Log the useage times
  remove-nbt:
    - 'HSTRY_ARMOR'
  mi-update-lore: true

# Match Item
match-item:
  items:
    - 'YourMMOItemsArmorID'

# Conditions
conditions:
  1:
    type: placeholder
    rule: '<='
    placeholder: '{nbt_UPGRADE_TIMES;;0}'
    value: 20 # Set the maxmium use times here.
    
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

# Conditions

Require PREMIUM version of MythicChanger.

Condition not means a match rule called condition, this page only tell you that you can also use Condition Fomrat to match the item you want in apply item configs! For example:

```yaml
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
```

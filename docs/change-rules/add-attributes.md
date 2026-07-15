# Add Attributes

## Add Attributes

```yaml
real-changes:
  add-attributes:
    ATTACK_DAMAGE: 
      name: attack_damage 
      amount: '{item_attributes.ATTACK_DAMAGE.amount;;0} + 1'
      operation: ADD_NUMBER 
      slot: HAND # Optional, if not set, plugin will auto set by material type
```

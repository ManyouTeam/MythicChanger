# MMOItems

{% hint style="warning" %}
This rule is not offical suppored by both MMOItems team and our team, as this part of codes is not offical public API from MMOItems, and MMOItems may change the logic all the time. I do not have patient to maintain all the time. Thanks.
{% endhint %}

## MMOItems Update Lore <mark style="color:red;">- Premium</mark>

```yaml
real-changes:
  mi-update-lore: true
```

This rule only work for real changes.&#x20;

This rule only support for items from MMOItems.

The Lore Update of MMOItems will also switch based on the NBT data values of history data, so it is advisable to remove all NBT data from history as well.

```yml
real-changes:
  add-nbt-double:
    - 'MMOITEMS_ATTACK_DAMAGE;;{nbt_MMOITEMS_ATTACK_DAMAGE;;1}*100'
  remove-nbt:
    - 'HSTRY_ATTACK_DAMAGE'
  mi-update-lore: true
```

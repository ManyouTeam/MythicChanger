# Replace Item

## Replace item\* <a href="#replace-new-item" id="replace-new-item"></a>

Use the ItemFormat [here](https://ultimateshop.superiormc.cn/base/item-format).

You need use this both in `fake-changes` and `real-changes` option to make it working well.

{% hint style="info" %}
The data of the original item will be lost. You can consider using rules starting with `keep` to convert some of the data from the original item to the new item (this will cause some additional performance consumption). For more info, please view [here](keep.md).
{% endhint %}

```yaml
fake-changes:
  replace-item:
    # Put Item Format here.
    hook-plugin: MMOItems
    hook-item: 'MATERIAL;;REAR_INGOT'
real-changes:
  replace-item:
    # Put Item Format here.
    hook-plugin: MMOItems
    hook-item: 'MATERIAL;;REAR_INGOT'
```

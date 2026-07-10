# Keep

## Keep <mark style="color:red;">- Premium</mark>

If a rule with the `*` symbol is used, the original item will disappear and a new item will replace the original item. Therefore, the data of the original item will disappear. If you want to retain some information about the original item, you need to use the rules here.

Support `keep-enchants, keep-name, keep-lore, keep-item-name, keep-flag, keep-damage` change rule.

<pre class="language-yaml"><code class="lang-yaml">fake-changes:
  keep-enchants: true
<strong>  keep-name: true
</strong>  keep-lore: true
  keep-item-name: true
  keep-flag: true
  keep-damage: true
</code></pre>

{% hint style="warning" %}
Keep is a standard change rule, not a option inside ItemFormat, for example:

```yaml
fake-changes:
  keep-enchants: true
  replace-item:
    # keep-enchants: true <--- This is wrong! Put keep rules here will not work!
    hook-plugin: MMOItems
    hook-item: 'MATERIAL;;REAR_INGOT'
```
{% endhint %}

## Keep Item Format <mark style="color:red;">- Premium</mark>

You can copy the item format of the original item to a new item and enter the key value of the item format to be copied in this rule. For example:

```yaml
real-changes:
  keep-item-format:
    - 'enchants'
```

You can parse hold item to ItemFormat by using `/mc generateitemformat` command. You can distinguish the hierarchical relationship of YAML through the `.` symbol.&#x20;

```yaml
material: diamond_sword
enchants:
  sharpness: 1
  unbreaking: 2
```

In above example, if you just enter `'enchants'` in the rule, we will copy all content below enchants, you can also use `'enchants.sharpness'` if you just want to copy sharpness enchant. The `.` symbol between `'enchants'` and `'sharpness'` represents their hierarchical relationship.

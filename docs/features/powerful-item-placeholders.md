# ⚡Powerful Item Placeholders

## Where can I use them?

* In MythicChanger itself, you can use powerful item placeholders at fake or real changes's config. Like this:

```yaml
real-changes:
  add-enchants:
    sharpness: '{item_enchants.sharpness;;0} + 1'
```

* You can also use them at actions or conditions set in MythicChanger.
* Not all! You can use them at other item plugins that not packet based in lore! Just use `parse-papi-lore` change rule do it! Use this rule in fake changes only! Example rule file:

{% code title="plugins/MythicChanger/example_placeholder.yml" %}
```yaml
match-item: []

fake-changes:
  parse-papi-name: true
  parse-papi-lore: true
```
{% endcode %}

## Placeholder List

* {name}
* {original-name} - The original item name, which means the item changed before.
* {raw-name} - The item name without color code. <mark style="color:red;">**(Premium)**</mark>
* {raw-original-name} - The original item name, but without any color code. <mark style="color:red;">**(Premium)**</mark>
* {amount}&#x20;
* {max-stack}
* {item\_\<Item Format Key>;;\<Default Value>} - Get the value of the item's specified Item Format value. You can use command `/mc generateitemformat` to view hold item's Item Format. <mark style="color:red;">**(Premium)**</mark>
* {nbt\_\<NBT Key>;;\<Default Value>} - Get the value of the item's specified Custom NBT value. You can use command `/mc viewnbt` to view hold item's Custom NBT. <mark style="color:red;">**(Premium, require NBTAPI)**</mark>
* ALL PLACEHOLDERAPI placeholders!

## Math

You can use placeholders and [Math Calculate Format](https://ultimateshop.superiormc.cn/format/math-calculate-format) in change configs! Just set `math.enabled` option to `true` in `config.yml` file and then enjoy!&#x20;

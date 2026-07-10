# Set Name/Lore

## Set name/Set Lore <a href="#set-name-set-lore" id="set-name-set-lore"></a>

Set custom display names or lore for items.&#x20;

{% hint style="info" %}
Please note: The placeholder `{name}` will includes item name's color code, which means you set set-name: `'&6{name}'` won't work because item itself will override the color code you set before. You need use `{raw-name}` instead, this placeholder will return the value don't include color code. This placeholder only available in <mark style="color:red;">**PREMIUM**</mark> version.
{% endhint %}

```yaml
fake-changes:
  set-name: '&fGood {name}' # Supported placeholder: {name}
  set-lore:
    - '&7A good sword that all adventures should use!'
    - '&f&lCommon'
```

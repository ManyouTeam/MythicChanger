# Contains Name/Contains Lore

{% hint style="info" %}
You shoud not put color code here if you enabled `ignore-color-code` option at `config.yml` file.
{% endhint %}

## Contains Name

Item display name must contain any listed text.

```yaml
match-item:
  contains-name:
    - 'test1'
```

## Contains Lore

Any lore line containing listed text will pass.

```yaml
match-item:
  contains-lore:
    - 'test1'
```

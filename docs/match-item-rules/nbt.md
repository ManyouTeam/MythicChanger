# NBT

## NBT String <mark style="color:red;">- Premium</mark>

Require NBTAPI, download it [here](https://www.spigotmc.org/resources/nbt-api.7939/).

Require specifed NBT type must be STRING.

Use `;;` distinguish key value hierarchy.&#x20;

The value after the last `;;` symbol represents the required NBT value.

```yaml
match-item:
  # Require this NBT value to be A.
  nbt-string:
    - 'PublicBukkitValues;;mythicchanger:apply_item_id;;A'
```

## NBT Int/NBT Double/NBT Byte <mark style="color:red;">- Premium</mark>

Require NBTAPI, download it [here](https://www.spigotmc.org/resources/nbt-api.7939/).

Similar to NBT String, but should has comparison symbols.

Comparsion symbols support:

* \>=
* <=
* \>
* <
* \==

```yaml
match-item:
  nbt-double:
    - 'PublicBukkitValues;;mythicchanger:test;;>=;;5'
```

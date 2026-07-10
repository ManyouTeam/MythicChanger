# NBT

## Add NBT\* <mark style="color:red;">- Premium</mark>

Require NBTAPI, download it [here](https://www.spigotmc.org/resources/nbt-api.7939/).

Support `add-nbt-string, add-nbt-int, add-nbt-double, add-nbt-byte` change rule.

Use `;;` distinguish key value hierarchy.&#x20;

The value after the last `;;` symbol represents the NBT value.

```yaml
real-changes:
  add-nbt-int:
    - 'test;;5'
  add-nbt-string:
    # Key;;Value
    - 'test;;t'
```

## Remove NBT\* <mark style="color:red;">- Premium</mark>

Require NBTAPI, download it [here](https://www.spigotmc.org/resources/nbt-api.7939/).

Use `;;` distinguish key value hierarchy.&#x20;

```yaml
real-changes:
  remove-nbt:
    - 'fff;;fff'
```

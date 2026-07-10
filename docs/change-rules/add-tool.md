# Add Tool

## Add Tool - 1.21+

`add-tool` is used to modify an item's vanilla **Tool Data Component**, allowing normal items or tools to behave with specified mining speed, durability consumption, and block-breaking rules on both the client and server.

### Basic Format

```yaml
real-changes:
  add-tool:
    mining-speed-multiplier: 1.5
    damage-per-block-add: 1
    rules:
      - 'minecraft:stone,2.0,true'
```

### Available Options

#### `mining-speed-multiplier`

Sets the default mining speed multiplier.

The value is calculated based on the item's current default mining speed from its existing Tool Component.

```yaml
add-tool:
  mining-speed-multiplier: 1.75
```

The old option name `mining-speed` is also supported and has the same effect as `mining-speed-multiplier`.

```yaml
add-tool:
  mining-speed: 1.75
```

#### `damage-per-block`

Directly sets the durability consumed each time a block is broken.

```yaml
add-tool:
  damage-per-block: 2
```

#### `damage-per-block-add`

Adds the specified value to the current durability consumption.

The final value will not be lower than `0`.

```yaml
add-tool:
  damage-per-block-add: 1
```

#### `rules`

Adds tool rules for specific blocks.

Format:

```yaml
rules:
  - 'Block ID,Mining Speed Multiplier,Correct For Drops'
```

Example:

```yaml
add-tool:
  rules:
    - 'minecraft:stone,2.5,true'
    - 'minecraft:oak_log,2.25,true'
```

You can also define multiple blocks in the same rule:

```yaml
add-tool:
  rules:
    - 'minecraft:stone,minecraft:deepslate,2.0,true'
```

The last two parameters are always:

```yaml
Mining Speed Multiplier,Correct For Drops
```

`Correct For Drops` can be:

| Value   | Description                                                                                |
| ------- | ------------------------------------------------------------------------------------------ |
| `true`  | This tool is considered the correct tool for breaking these blocks, allowing normal drops. |
| `false` | This tool is not considered the correct tool.                                              |
| `null`  | Does not override the vanilla judgment.                                                    |

### Full Example

```yaml
real-changes:
  add-lore-last:
    - '&7Affix: &aQuarry Heart'
  add-attributes:
    block_break_speed:
      name: 'mythicchanger:tool_quarry_heart'
      amount: 1.6
      operation: ADD_NUMBER
      slot: HAND
  add-tool:
    mining-speed-multiplier: 1.75
    damage-per-block-add: 1
    rules:
      - 'minecraft:stone,2.5,true'
      - 'minecraft:deepslate,2.0,true'
```

### Effect Explanation

The configuration above will make the item:

* Gain additional lore.
* Gain an extra `block_break_speed` attribute.
* Increase its default mining speed to `1.75x` of the original value.
* Consume 1 additional durability point each time a block is broken.
* Use `2.5x` mining speed for `stone` and treat it as the correct tool.
* Use `2.0x` mining speed for `deepslate` and treat it as the correct tool.

### Notes

* `add-tool` only works on **Minecraft 1.21+**.
* If the item does not already have a suitable Tool Component, it is recommended to use this feature mainly on vanilla tool items such as pickaxes, axes, shovels, hoes, shears, and brushes.
* If you only want to display information without actually changing tool behavior, use lore-related rules under `fake-changes`.
* If you want to actually change mining behavior, use `add-tool` under `real-changes`.

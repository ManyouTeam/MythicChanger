# Testing Paper Components

Use a server running the Paper version you want to test and MythicChanger Premium. Configuration parsing and a successful plugin build do not verify game behavior. Check the item data, the visible result and a save/rebuild cycle separately.

## Choose a version-specific ItemFormat

Each sample is a complete ItemFormat. Download or copy its raw YAML content, then save it as `plugins/MythicChanger/items/pct-all.yml`:

| Server version | Example |
| --- | --- |
| Paper 1.21.5 | [1.21.5 component test](examples/paper-1.21.5-components.yml) |
| Paper 1.21.6 | [1.21.6 component test](examples/paper-1.21.6-components.yml) |
| Paper 1.21.11 | [1.21.11 component test](examples/paper-1.21.11-components.yml) |
| Paper 26.1 | [26.1 component test](examples/paper-26.1-components.yml) |
| Paper 26.2 | [26.2 component test](examples/paper-26.2-components.yml) |
| Paper 26.3 | [26.3 component test](examples/paper-26.3-components.yml) |

These samples combine many supported components for data inspection. They omit the head profile, nested bundle/projectile/remainder tests and selected tooltip options so that those can be checked separately. They cover the plugin's supported configuration, rather than every native Minecraft component or every possible value.

Install only the sample matching your server version under this file name. A newer sample on an older server can leave unsupported options unapplied. Do not interpret that as a successful test of those components.

Reload and give yourself the item:

```text
/mc reload
/mc givesaveitem pct-all
```

For a console command, include an online player name:

```text
mc givesaveitem pct-all PlayerName 1
```

The give command requires `mythicchanger.givesaveitem`. The item ID is the file name without `.yml`; do not include the `items/` path in the command.

## Inspect the generated data

Hold the item in your main hand and run:

```text
/data get entity @s SelectedItem.components
```

Run this as a player with access to the vanilla `data` command. The native component names use underscores, while the plugin's YAML configuration usually uses hyphens. For example, `custom-model-data` is written to `minecraft:custom_model_data`.

Check the relevant component values rather than only whether the item exists. A component might be stored without producing a visible effect on the sample's `DIAMOND_SWORD` material.

The combined sample is not a reliable way to test every interaction at once. Equipping, blocking and eating can compete for the same use action. It also includes `unbreakable: true`, so it should not be used to test normal durability loss. For behavior tests, use a separate item with an appropriate material and only the component being tested, plus its prerequisites.

## Save and rebuild the item

In `config.yml`, set:

```yaml
debuild-item-method: COMPONENT
```

Reload the plugin. Hold the test item and run:

```text
/mc generateitemformat
```

The command writes `plugins/MythicChanger/generated-item-format.yml`. It requires `mythicchanger.generateitemformat` and overwrites the same file on every use, so copy each result before testing another item.

Copy the generated file to `plugins/MythicChanger/items/pct-roundtrip.yml`, then run:

```text
/mc reload
/mc givesaveitem pct-roundtrip
```

Hold the rebuilt item and inspect `SelectedItem.components` again. Compare component values, quantities and behavior. YAML key order, explicit defaults and generated player-profile UUIDs can differ without changing the intended item properties.

You can also save directly into the `items` folder:

```text
/mc saveitem pct-roundtrip itemformat
/mc givesaveitem pct-roundtrip
```

Saving requires `mythicchanger.saveitem`. The `itemformat` save method follows `debuild-item-method`; the `bukkit` save method uses the separate item serialization path and does not exercise the component-to-ItemFormat writer.

## Focused checks

| Test | Expected result |
| --- | --- |
| Custom model data | Each configured float, flag, string and color appears once and keeps its order after saving and rebuilding |
| Consumable effects | Each numbered effect adds only its configured type; effect duration, amplifier, particles and icon flags survive a save/rebuild cycle |
| Suspicious stew | `effect ID,duration` entries work without a third field |
| Blocking | Saved `block-delay-seconds` remains a number, independent of `block-sound` |
| Damage resistance, Paper 26.1+ | A tag string and a list of individual damage IDs both preserve their respective formats |
| Attributes, Paper 1.21.6+ | Explicit `RESET`, `HIDDEN` and `OVERRIDE` modes produce the intended tooltip display |
| Weapon components, Paper 1.21.11+ | Inspect the configured reach, charge, kinetic/piercing weapon and use-effect data; test each behavior separately |
| Animation compatibility, Paper 26.3 | `swing-animation` supplies both native animations; explicit attack/interact sections override their own side |
| Signs, Paper 26.3 | Placed signs retain the configured front/back text, dye colors, glowing state and wax |

For a consumable effect test, use the complete apple example in [Consumables - 1.21.5+](component-format.md#consumables). It should apply Speed I for 200 ticks and play a level-up sound. It should not also teleport the player or remove effects unless those types were configured. Test in survival mode.

## Nested-item regression examples

Save each example in its own `items/<ID>.yml` file and use `/mc givesaveitem <ID>` to obtain it. Run the save/rebuild procedure for each item.

### Bundle contents

Save as `pct-bundle.yml`:

```yaml
material: BUNDLE
amount: 1

component:
  bundle-contents:
    '1':
      material: DIAMOND
      amount: 2
      component:
        name: '<aqua>Diamond inside bundle'
    '2':
      material: EMERALD
      amount: 3
```

The bundle should contain two diamonds and three emeralds. The diamonds should retain their custom name. Nested entries must not turn into stone or lose their amounts.

### Charged crossbow

Save as `pct-projectile.yml`:

```yaml
material: CROSSBOW
amount: 1

component:
  charged-projectiles:
    '1':
      material: ARROW
      amount: 1
```

The crossbow should contain an arrow and remain loaded after rebuilding. Test shooting it separately from the saved data inspection.

### Item returned after consumption

Save as `pct-remainder.yml`:

```yaml
material: APPLE
amount: 1

component:
  food:
    nutrition: 1
    can-always-eat: true

  consumable:
    consume-seconds: 1.0
    animation: EAT
    sound: minecraft:entity.generic.eat

  use-reminder:
    material: GLASS_BOTTLE
    amount: 1
    component:
      name: '<yellow>Bottle returned after use'
```

Eat the apple in survival mode. It should return a named glass bottle. After saving and rebuilding the unused apple, it should still return the same item. The saved `use-reminder` must contain the nested ItemFormat directly, without an additional `item:` wrapper.

## Resource packs and reports

Custom model data, custom item models and custom tooltip styles need matching resource pack assets to change appearance. A missing visual asset does not establish that the component failed to write. Use registry IDs that exist on the tested server, including any data packs it loads.

When reporting a problem, include the exact Paper build, MythicChanger version, original ItemFormat, generated ItemFormat, relevant item component data and any console exception. State whether the problem occurs while giving, using, saving or rebuilding the item. Do not report all six versions as tested when only their configuration files were parsed.

# 📝ItemFormat™

MythicChanger uses the same ItemFormat component keys as UltimateShop. Keep `material` and `amount` at the root, and put Paper component options under `component`.

```yaml
material: DIAMOND_SWORD
amount: 1

component:
  name: '<aqua>Component sword'
  glow: true
  damage: 25
  max-damage: 2048
```

Paper components require Paper 1.21.5+ and MythicChanger Premium. Newer components require the server version shown in each component heading.

- [Component Format](component-format.md): each supported component, its configuration and minimum Paper version.
- [Testing Paper Components](component-testing.md): version-specific ItemFormat examples and save/rebuild checks using MythicChanger commands.

Set `debuild-item-method: COMPONENT` in `config.yml` to save supported item properties inside `component` with `/mc generateitemformat` or `/mc saveitem <ID> itemformat`. This setting controls saving; it is not required to build an ItemFormat that already contains a `component` section.

Custom ItemFormat files go in `plugins/MythicChanger/items/`. Reload with `/mc reload`, then give an item with `/mc givesaveitem <ID>`. The ID is the file name without `.yml`.

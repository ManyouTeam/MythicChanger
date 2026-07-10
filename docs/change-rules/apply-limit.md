# Apply Limit

## Add apply limit/Set apply limit/Reset apply limit <mark style="color:red;">- Premium</mark>

It doesn't mean a limit on the number of times an apply item is used, but a limit on the type of apply item. If you need to limit the maximum number of times an apply item can be used, you can flexibly set the match item based on the apply item. For example, this apply item can add a level to the item's enchantment each time. Then, when the item's enchantment level is greater than a certain value, it can be considered as using the apply item a certain number of times, or you can add a double type nbt to it every time you use the apply item. Add 1 to the value of this nbt every time it is used, and the final value stored in nbt is the number of times it has been used. For example of set limit of apply item use times, please view this [example file](../drag-gui-change/apply-item-example-stat-scroll-with-usage-times-limit.md).

```yaml
real-changes:
  add-apply-limit: 5
  set-apply-limit: 15
  reset-apply-limit: true
```

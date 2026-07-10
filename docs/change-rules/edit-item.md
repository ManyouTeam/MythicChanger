# Edit Item

## Edit Item

Edit the item by Item Format.

Some change rule can be done by Item Format, for example:

For set name rule:

```yaml
fake-changes:
  set-name: 'test1'
```

can be replaced to:

```yaml
fake-changes:
  edit-item:
    name: 'test1'
```

View Item Format [here](https://ultimateshop.superiormc.cn/base/item-format). You can parse hold item to ItemFormat by using `/mc generateitemformat` command.

## Edit Item and Replace\*

If you want to rewrite item, you can use `replace-item` option in this rule.

```yaml
fake-changes:
  edit-item:
    name: 'test1'
    repalce-item: true
```

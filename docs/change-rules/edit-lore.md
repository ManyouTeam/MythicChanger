# Edit Lore

## Edit Lore <mark style="color:red;">- Premium</mark>

Edit specifed line's lore. Format is `line number (or last to mean the last line): The modified lore text (support use {original} to mean the original lore line text)`.

```yaml
fake-changes:
  edit-lore:
    30: 'test'
    last: '&f{original} hello!'
  edit-lore-bypass: false
```

In this example, we set item's lore value to test in line 30, however, if this item don't have 30 lines, we will only try add new line for this item. If you don't want this and just want us ignore the exceeding line, please use `edit-lore-bypass option`.

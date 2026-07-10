# Not Match Actions - Premium

You can add `not-match-actions` in `match-item` section, like this:

```yaml
match-item:
  has-name: false
  not-match-actions:
    1:
      type: message
      message: 'Your item already has name, it can not be changed!'
```

Should follow Action Format, for more info, please view [Format](../format/action-format.md) page.

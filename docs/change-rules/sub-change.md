# Sub Change

## Sub Change <mark style="color:red;">- PREMIUM</mark>

Sometimes, you can continue to filter under the item you want to match, and this rule can help you achieve this.\
In this example, match item first helped you filter out all the swords, and in the sub change rule, we further divided the diamond sword and the golden sword into two different change rules.

```yaml
match-item:
  material-tag:
    - 'swords'

real-changes:
  # If set to true, item will only be changed once in sub change rule.
  sub-change-match-one: false
  sub-change:
    1:
      match-item:
        material:
          - diamond_sword
      changes:
        add-enchants:
          knockback: 1
      actions:
        1:
          type: message
          message: 'Cool!'
    2:
      match-item:
        material:
          - golden_sword
      changes:
        add-enchants:
          unbreaking: 1
```

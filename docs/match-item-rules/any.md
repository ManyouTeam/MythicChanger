# ANY

## ANY <mark style="color:red;">- Premium</mark>

If item meet any of those match item rules, we will consider it meet the rule.

```yaml
match-item:
  any:
    rarity: COMMON
    material:
      - 'diamond'
```

## ANY Of <mark style="color:red;">- Premium</mark>

```yaml
match-item:
  any:
    1: 
      contains-name:
        - 'Hello'
      contains-lore:
        - 'Oh'
    2:
      contains-lore:
        - 'What'
```

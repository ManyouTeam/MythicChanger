# Set Amount/Disappear

## Set Amount <a href="#add-flag-remove-flag" id="add-flag-remove-flag"></a>

Supported placeholder:&#x20;

* {amount} - Now item amount, don't use it in fake changes.
* {max-stack} - Get this material max stack size, for example diamond's max stack size is 64.

```yaml
real-changes:
  set-amount: 5 
```

## Disappear

Just try set item amount to 0.

```yaml
real-changes:
  set-amount: 0
```

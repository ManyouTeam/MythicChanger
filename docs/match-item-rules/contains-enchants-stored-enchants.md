# Contains Enchants/Stored Enchants

{% hint style="info" %}
The `contains-enchants` rule itself already support stored enchants in enchanted book, so you don't need change the rule ID to `contains-stored-enchants`.
{% endhint %}

## Contains Enchants/Stored Enchants <mark style="color:red;">- Premium</mark>

Similar to `has-enchants`, but require the enchantment level must be greater than the value you set.

```yaml
match-item:
  contains-enchants:
    POWER: 1
```

## Contains specified levels enchants/stored Enchants <mark style="color:red;">- Premium</mark> <a href="#remove-enchants" id="remove-enchants"></a>

If you want to make only specified enchantment level take effect, you can use int list at here. For example, I want to make only item has Power level 1 enchantment match, then you should set this section like this:

```yaml
match-item:
  contains-enchants:
    POWER: [1] 
    OTHER: [1,2,5] # Means level1, 2 and 5.
```

# Damage

## Add Damage

{% code title="" %}
```yml
real-changes:
  add-damage: 10
```
{% endcode %}

## Repair Damage

{% code title="" %}
```yml
real-changes:
  repair-damage: 10
```
{% endcode %}

## Damage Modify

This will directly modify item damage to 100.

{% code title="" %}
```yml
real-changes:
  damage: 100
```
{% endcode %}

Advanced format:

{% code title="" %}
```yml
real-changes:
  damage:
    operation: ADD
    value: 10
```
{% endcode %}

For operation option, supports value:

* SET
* ADD
* SUBTRACT
* MULTIPLY

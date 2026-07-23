# 📚Rule Config Example - Random Replace

{% hint style="info" %}
This example file will only work for <mark style="color:red;">**PREMIUM**</mark> version of **MythicChanger**.
{% endhint %}

```yaml
weight: 15

match-item:
  material:
    - iron_sword
  has-name: false

fake-changes: 
  replace-item:
    material: iron_sword
    name: '&c???'
    lore:
      - '&fNo one knows what sword'
      - '&fyou will obtain...'

real-changes: 
  replace-random-item:
    1:
      material: iron_sword
      amount: 1
      name: §fSharp §fLong Sword
      lore:
      - §cSword
      - §f
      - §eRequires Lvl 0
      - §f
      - '§3 §7➸ Attack Damage: §f8.1'
      - '§3 §7➸ Attack Speed: §f1.6'
      - §f
      - §c🔥§7 10 Fire Damage
      - §7Much sharper!
      flags:
      - HIDE_ATTRIBUTES
      attributes:
        - type: GENERIC_ATTACK_SPEED
          name: decoy
          amount: 0.0
          operation: ADD_NUMBER
          slot: any
      rate: 1
    2:
      material: iron_sword
      amount: 1
      name: §fKatana
      lore:
      - §cSword
      - §f
      - '§3 §7➸ Attack Damage: §f7'
      - '§3 §7➸ Attack Speed: §f2'
      - '§3 §7■ Crit Strike Chance: §f+10%'
      - '§3 §7■ Movement Speed: §f+2'
      - §f
      - §7§oA very sharp & light sword
      - §7§oforged by a hundred years
      - §7§oof smithing mastery.
      - §f
      - '§3 §7Tier: §6§lRARE'
      - '§7Durability: 800 / 800'
      flags:
      - HIDE_ATTRIBUTES
      attributes:
        - type: GENERIC_ATTACK_SPEED
          name: decoy
          amount: 0.0
          operation: ADD_NUMBER
          slot: any
      rate: 1

real-change-actions: []

conditions: []
    
only-in-player-inventory: false
```

# 📚Apply Item Example - Item Skin Scroll

{% hint style="info" %}
This example file will only work for <mark style="color:red;">**PREMIUM**</mark> version of **MythicChanger**.
{% endhint %}

{% code title="item_skin.yml" %}
```yaml
# PREMIUM VERSION ONLY

apply-item-mode: GENERAL

# Display Item
display-item:
  material: PAPER
  name: '&fItem Skin &8(Diamond Sword)'
  lore:
    - '&7Drag this item onto the item you want to apply.'
  max-stack: 1

# Rule Configs
real-changes:
  edit-item:
    custom-model-data: 15
  add-nbt-int:
    - 'original_custom_model_data;;{item_custom-model-data;;-1}'
  add-nbt-string:
    - 'used_skin_id;;item_skin'

# Match Item
match-item:
  item-format:
    material: diamond_sword
  item-format-settings:
    require-same-key: false
  not-match-actions:
    1:
      type: message
      message: '&c☹ &fYour sword type is not diamond sword!'

# Conditions
conditions:
  1:
    type: placeholder
    placeholder: '{nbt_used_skin_id;;0}'
    rule: '=='
    value: '0'
    not-meet-actions:
      1:
        type: message
        message: '&c☹ &fThis item already includes item skin!'

# Actions
success-actions:
  1:
    type: message
    message: '&d✦ &fCool, it looks like magic is showing off, your {name} &fhas skin now!'
```
{% endcode %}

{% code title="item_skin_rollback.yml" %}
```yaml
# PREMIUM VERSION ONLY

apply-item-mode: GENERAL

# Display Item
display-item:
  material: PAPER
  name: '&fItem Skin Rollback &8(Diamond Sword)'
  lore:
    - '&7Drag this item onto the item have skin to rollback.'
  max-stack: 1

# Rule Configs
real-changes:
  sub-change-match-one: true
  sub-change:
    1:
      conditions:
        1:
          type: placeholder
          placeholder: '{nbt_original_custom_model_data;;-1}'
          rule: '<'
          value: 0
      changes:
        remove-custom-model-data: true
        remove-nbt:
          - 'original_custom_model_data'
          - 'used_skin_id'
    2:
      conditions:
        1:
          type: placeholder
          placeholder: '{nbt_original_custom_model_data;;-1}'
          rule: '>='
          value: 0
      changes:
        set-custom-model-data: '{nbt_original_custom_model_data;;-1}'
        remove-nbt:
          - 'original_custom_model_data'
          - 'used_skin_id'

# Match Item
match-item:
  item-format:
    material: diamond_sword
  item-format-settings:
    require-same-key: false
  contains-nbt:
    - 'original_custom_model_data'
  not-match-actions:
    1:
      type: message
      message: '&c☹ &fThis sword does not include valid skin!'

# Actions
success-actions:
  1:
    type: message
    message: '&d✦ &fCool, it looks like magic is showing off, your {name} &fis rollback!'
  # Give back the skin item.
  2:
    type: console_command
    command: 'mc giveapplyitem {nbt_used_skin_id} {player}'
```
{% endcode %}

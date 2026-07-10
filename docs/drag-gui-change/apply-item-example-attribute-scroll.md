# 📚Apply Item Example - Attribute Scroll

{% hint style="info" %}
This example file will only work for <mark style="color:red;">**PREMIUM**</mark> version of **MythicChanger**.
{% endhint %}

<pre class="language-yaml"><code class="lang-yaml"><strong># Display Item
</strong><strong>display-item:
</strong>  material: PAPER
  name: '&#x26;#da8f87Attribute Scroll &#x26;7(Attack Damage &#x26;a+0.5&#x26;7)'
  lore:
    - '&#x26;8Scroll'
    - ''
    - '&#x26;7Drag any &#x26;cswords or axes &#x26;7onto this item to'
    - '&#x26;7make its &#x26;bAttack Damage &#x26;7value &#x26;a+0.5&#x26;7.'
    - '&#x26;7Up to value &#x26;410&#x26;7 can be upgraded.'
    - ''
    - '&#x26;bSuccess Rate: &#x26;a50%'
    - '&#x26;a&#x26;lRare'
  max-stack: 1
  
# Chance, Up to 100
chance: 50
  
# Rule Configs
real-changes:
  add-attributes:
    ATTACK_DAMAGE: 
      name: attack_damage 
      amount: '0.5'
      operation: ADD_NUMBER 
      slot: HAND 

# Match Item
match-item:
  material-tag:
    - swords

# Conditions
conditions:
  1:
    type: placeholder
    rule: '&#x3C;'
    placeholder: '{item_attributes.ATTACK_DAMAGE.amount;;0}'
    value: 10
    
# Actions
success-actions:
  1:
    type: message
    message: '&#x26;fCool, it looks like magic is showing off, your {name} &#x26;fis more powerful now!'
fail-actions:
  1:
    type: message
    message: '&#x26;fOh, no! Seems that this time you are now very lucky, the scroll broken, nothing changed!'
</code></pre>

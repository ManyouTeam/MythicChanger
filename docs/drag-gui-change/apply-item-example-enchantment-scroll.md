# 📚Apply Item Example - Enchantment Scroll

{% hint style="info" %}
This example file will only work for <mark style="color:red;">**PREMIUM**</mark> version of **MythicChanger**.
{% endhint %}

<pre class="language-yaml"><code class="lang-yaml"># Display Item
display-item:
  material: PAPER
  name: '&#x26;fEnchantment Scroll &#x26;7(Respiration &#x26;a+1&#x26;7)'
  lore:
    - '&#x26;8Scroll'
    - ''
    - '&#x26;7Drag any &#x26;cenchantable item &#x26;7onto this item to'
    - '&#x26;7make its &#x26;bRespiration &#x26;7level &#x26;a+1&#x26;7.'
    - '&#x26;7Up to level &#x26;410&#x26;7 can be upgraded.'
    - ''
    - '&#x26;buccess Rate: &#x26;a50%'
    - '&#x26;9&#x26;lUncommon'
  max-stack: 1
  
# Chance, Up to 100
chance: '50'
  
# Rule Configs
real-changes:
  add-enchants:
    respiration: '{item_enchants.respiration;;0} + 1'

# Match Item
match-item:
  enchantable:
    - respiration

# Conditions
conditions:
  1:
    type: placeholder
    rule: '&#x3C;'
    placeholder: '{item_enchants.respiration}'
    value: 10
    
<strong># Actions
</strong>success-actions:
  1:
    type: message
    message: '&#x26;fCool, it looks like magic is showing off, your {name} &#x26;fis more powerful now!'
fail-actions:
  1:
    type: message
    message: '&#x26;fOh, no! Seems that this time you are now very lucky, the scroll broken, nothing changed!'
</code></pre>

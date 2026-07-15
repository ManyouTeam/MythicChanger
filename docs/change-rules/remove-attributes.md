# Remove Attribute

## Remove Attribute

<pre class="language-yaml" data-title=""><code class="lang-yaml"><strong>real-changes:
</strong><strong>  remove-attributes:
</strong>    - armor
    - movement_speed
</code></pre>

## Remove Attribute By/Contains Name

{% code title="" %}
```yaml
real-changes:
  remove-attribute-by-name:
    - 'mythicchanger:armor_bulwark'

  remove-attribute-contains-name:
    - 'mythicchanger:armor_'
```
{% endcode %}

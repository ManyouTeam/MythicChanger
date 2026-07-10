# Add Name

## Add name first/Add name last <a href="#add-name-first-add-name-last" id="add-name-first-add-name-last"></a>

Add new content to the left or right of the custom display name of the item.

{% hint style="info" %}
If you are getting name dupe, that is simply because your item name has actually changed in both server and client which it shouldn't be.\
\
You use **MythicChanger** to add`XX` at the start or end of the item name. After changing the name, if the real name of the item will change, and the changed item will still meet **MythicChanger**'s matching condition, MythicChanger will keep adding XX at the start or end of the item name.\
\
To solve this, you should manually add a `contains-name` matching rule to avoid **MythicChanger** keep modifiying item, also the name will not be able to update by us anymore.\
\
The real change don't always happen, only your other plugins or anvil can do that! Ban anvil rename item name is best choice.\
\
Many people use this rule for Custom Tooltips, what I want to say is: Why you don't use Lore's first line to cover the name of the item, because this not only solves the problem of players being able to modify Tooltips in the anvil, but also solves the problem of seeing Tooltips when switching items in the shortcut bar.
{% endhint %}

```yaml
fake-changes:
  add-name-first: '&fGood '
  add-name-last: ' &d(New)'
```

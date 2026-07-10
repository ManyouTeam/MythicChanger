# ⚡Apply Item Mode

## Enable/Disable Apply Item Mode

We support 2 apply item use method, you can enable or disable it at `config.yml` with `apply-item-mode`. With this option, it will work for all apply items in plugin.

* GUI: Apply items will use in `/mc openchangegui`. <mark style="color:red;">**- Premium**</mark>
* DRAG: Drag the apply item to the item you want to changed.

This option change require server restart.

```yaml
apply-item-mode:
  drag-enabled: true
  # GUI mode is in EARLY ALPHA TEST, be careful to use!
  # Premium version only.
  gui-enabled: true
```

## Set apply item mode for each single apply items

If you want to set the apply mode for individual apply items, instead of setting it for all apply items at once as described above, then you need to set the value of the `apply-item-mode` option in `config.yml` to `GENERAL` first.

Then, each apply item configs will include a option called `apply-item-mode` too, set it to the apply use method you want. Supported value:

* GUI: Apply items will use in `/mc openchangegui`. <mark style="color:red;">**- Premium**</mark>
* DRAG: Drag the apply item to the item you want to changed.
* GENERAL: Both GUI and DRAG method can use the apply item. (GUI mode requires PREMIUM version)
* Other Words (Custom Mode): You can enter characters other than **DRAG, GUI, and GENERAL**. For example, if you enter `MY_CUSTOM` here, the player will only be able to use the apply item in the GUI opened by the command `/mc openchangegui MY_CUSTOM`.

{% hint style="info" %}
To open custom mode GUI, player need has mythicchanger.openchangegui.\<customModeName> permission, in this example, to use `/mc openchangegui MY_CUSTOM`, player need has `mythicchanger.openchangegui.MY_CUSTOM` permission.    &#x20;
{% endhint %}

<figure><img src="../.gitbook/assets/1f1642c4fc54ac97e40b9e3dd59e9c56.png" alt=""><figcaption></figcaption></figure>

## Custom Mode <mark style="color:red;">- Premium</mark>

We've already introduced custom mode above. You can set `apply-item-mode` to a custom value in the apply item config, and then open a custom GUI using `/mc openchangegui` with your custom value. Players will only be able to use these apply items within this custom GUI.

You can also set those custom GUI config at `config.yml` file.

```yaml
# Default Change GUI
change-gui:
  title: 'Change your item!'
  custom-mode-title:
    MY_CUSTOM: 'MY_CUSTOM change GUI'
  size: 27
  item-slot: 11
  book-slot: 15
  confirm-slot: 22
  confirm-item:
    material: ANVIL
    name: '&eConfirm'
    lore:
      - '&7Click to confirm change the item!'
  custom-item:
    2:
      material: PAPER
      name: '&bDrag the item you want change below.'
    6:
      material: BOOK
      name: '&bDrag the apply item below.'
  close-if-fail: true

# MY_CUSTOM GUI
MY_CUSTOM:
  title: 'MY_CUSTOM'
  custom-mode-title:
    MY_CUSTOM: 'MY_CUSTOM change GUI'
  size: 27
  item-slot: 11
  book-slot: 15
  confirm-slot: 22
  confirm-item:
    material: ANVIL
    name: '&eConfirm'
    lore:
      - '&7Click to confirm change the item!'
  custom-item:
    2:
      material: PAPER
      name: '&bDrag the item you want change below.'
    6:
      material: BOOK
      name: '&bDrag the apply item below.'
  close-if-fail: true
```

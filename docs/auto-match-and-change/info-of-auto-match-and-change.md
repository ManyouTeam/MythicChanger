# 📂Info of Auto Match & Change

The Auto Match\&Change feature refers to the plugin automatically recognizing all items of all players. Once a player's item meets the set matching requirements, it will undergo fake or real change.

Some common uses include:

* Automatically add rarity tags to all items.&#x20;
* Remove the enchantment added abnormally.&#x20;
* Remove suspected abnormal items.&#x20;
* Add the texture of the tooltip at the lore & name of the item.

## Fake Change and Real Change

The modification of items is divided into fake change and real change.

Fake change is display virtually, and it only change in client side. This means the modified content is only valid for the client. For the server, these modifications are false and have not actually taken effect.&#x20;

Real change is change both in client and server side, **modification of changes is not immediate in some time, but mostly in survival mode, real change is "immediate".**

For example, if you are using remove\_enchants change rule, if you set this rule in fake changes, item just display it don't have this enchantment, but actually it has and the enchantment will also take effect. If you set it in real change, then the enchantment will actually gone. No one will find it anymore.

## Rule

The Auto Match\&Change feature is implemented by various **rule** configuration files, which mainly include:

* Match Item: Set matching items through various **match rules**.
* Fake/Real Changes: The matched items will undergo **fake change and real change** according to the **change rule**.

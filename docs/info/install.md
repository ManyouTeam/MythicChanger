# ⚙️Install

## Install <a href="#install" id="install"></a>

* Put the `.jar` file into your server's `plugins` folder.
* Stop your server and then restart it. <mark style="color:red;">Cannot load plugins in any other way while the server is starting</mark>.
* When updating plugins, please be sure to remove old versions.
* Previously, you used the free version, but now to upgrade to the paid version, you only need to install the paid version on the server and remove the free version. The configuration file of the plugin does not require any changes.
* When downgrading from **a new game version** to **an old version** on the server where the plugin is located, it is important to remove the `items` folder from the configuration file.
* When using the [Localized Item Name](../features/localized-item-name-premium.md) feature, it is important to note that when switching server game versions, the previously generated localized files need to be deleted.
* If you just want to use our match item feature without any need of change item, you can disable `packet-listener` option at `config.yml` file to improve plugin performance and disable auto item change feature.

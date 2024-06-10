package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.ErrorManager;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetLore extends AbstractChangesRule {

    public SetLore() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack original, ItemStack item, Player player, boolean fakeOrReal) {
        if (fakeOrReal && !ConfigManager.configManager.getBoolean("ignore-fake-change-warning")) {
            ErrorManager.errorManager.sendErrorMessage("§x§9§8§F§B§9§8[MythicChanger] §6Warning: set-lore rule has incompatibility issues with" +
                    " other packet based item plugins, it is recommend that remove it in fake changes from all your rule configs!" +
                    " If you want to ignore this warning, please disable warning display in config.yml file.");
        }
        ItemMeta meta = item.getItemMeta();
        meta.setLore(TextUtil.getListWithColorAndPAPI(section.getStringList("set-lore"), player));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return 6;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("set-lore").isEmpty();
    }
}

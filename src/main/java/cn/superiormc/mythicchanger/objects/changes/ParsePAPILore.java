package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ParsePAPILore extends AbstractChangesRule {

    public ParsePAPILore() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section,
                               ItemStack original,
                               ItemStack item,
                               Player player,
                               boolean fakeOrReal,
                               boolean isPlayerInventory) {
        if (!section.getBoolean("parse-papi-lore")) {
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            return item;
        }
        List<String> lore = CommonUtil.modifyList(meta.getLore(),
                "name", ItemUtil.getItemName(item));
        meta.setLore(TextUtil.getListWithColorAndPAPI(lore, player));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("parse-papi-lore", 500);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("parse-papi-lore") == null;
    }
}

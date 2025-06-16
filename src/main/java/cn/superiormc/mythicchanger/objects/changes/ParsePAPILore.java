package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ParsePAPILore extends AbstractChangesRule {

    public ParsePAPILore() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (!singleChange.getBoolean("parse-papi-lore")) {
            return singleChange.getItem();
        }
        ItemMeta meta = singleChange.getItemMeta();
        if (!meta.hasLore()) {
            return singleChange.getItem();
        }
        List<String> lore = meta.getLore();
        meta.setLore(TextUtil.getListWithColorAndPAPI(singleChange.parsePlaceholder(lore), singleChange.getPlayer()));
        return singleChange.setItemMeta(meta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("parse-papi-lore", 500);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("parse-papi-lore");
    }
}

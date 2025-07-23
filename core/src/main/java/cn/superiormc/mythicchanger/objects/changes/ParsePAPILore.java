package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
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
        List<String> lore = MythicChanger.methodUtil.getItemLore(meta);
        MythicChanger.methodUtil.setItemLore(meta,
                lore,
                singleChange.getPlayer());
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

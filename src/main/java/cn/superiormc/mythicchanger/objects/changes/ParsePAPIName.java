package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ParsePAPIName extends AbstractChangesRule {

    public ParsePAPIName() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (!singleChange.getBoolean("parse-papi-name")) {
            return singleChange.getItem();
        }
        ItemMeta meta = singleChange.getItemMeta();
        if (!meta.hasDisplayName()) {
            return singleChange.getItem();
        }
        meta.setDisplayName(TextUtil.parse(meta.getDisplayName(), singleChange.getPlayer()));
        return singleChange.setItemMeta(meta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("parse-papi-name", 501);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("parse-papi-name");
    }
}

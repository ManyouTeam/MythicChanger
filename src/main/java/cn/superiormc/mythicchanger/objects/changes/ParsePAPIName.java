package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ParsePAPIName extends AbstractChangesRule {

    public ParsePAPIName() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack original, ItemStack item, Player player, boolean fakeOrReal) {
        if (!section.getBoolean("parse-papi-name")) {
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName()) {
            return item;
        }
        meta.setDisplayName(TextUtil.parse(meta.getDisplayName(), player));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("parse-papi-name", 501);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("parse-papi-name") == null;
    }
}

package cn.superiormc.mythicchanger.objects.changes;

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
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player, boolean fakeOrReal) {
        if (!section.getBoolean("parse-papi-lore")) {
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            return item;
        }
        meta.setLore(TextUtil.getListWithColorAndPAPI(meta.getLore(), player));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return 500;
    }
}

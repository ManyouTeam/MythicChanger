package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AddLorePrefix extends AbstractChangesRule {

    public AddLorePrefix() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player, boolean fakeOrReal) {
        if (section.getString("add-lore-prefix") == null) {
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            return item;
        }
        List<String> itemLore = meta.getLore();
        List<String> newLore = new ArrayList<>();
        for (String lore : itemLore) {
            if (lore.startsWith("§w")) {
                newLore.add(lore);
                continue;
            }
            newLore.add("§w" + TextUtil.parse(section.getString("add-lore-prefix") + lore, player));
        }
        meta.setLore(newLore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return 13;
    }
}

package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AddLoreFirst extends AbstractChangesRule {

    public AddLoreFirst() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player) {
        if (section.getStringList("add-lore-first").isEmpty()) {
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        List<String> itemLore = TextUtil.getListWithColorAndPAPI(section.getStringList("add-lore-first"), player);
        if (meta.hasLore()) {
            itemLore.addAll(meta.getLore());
        }
        meta.setLore(itemLore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return 11;
    }
}

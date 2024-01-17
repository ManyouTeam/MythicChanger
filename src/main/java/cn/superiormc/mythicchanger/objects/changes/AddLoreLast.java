package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AddLoreLast extends AbstractChangesRule {

    public AddLoreLast() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player) {
        if (section.getStringList("add-lore-last").isEmpty()) {
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        List<String> itemLore = meta.getLore();
        if (itemLore == null) {
            itemLore = new ArrayList<>();
        }
        itemLore.addAll(TextUtil.getListWithColorAndPAPI(section.getStringList("add-lore-last"), player));
        meta.setLore(itemLore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return 12;
    }
}

package cn.superiormc.mythicchanger.objects.changes;

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
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player) {
        if (section.getStringList("set-lore").isEmpty()) {
            return item;
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
}

package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AddNameLast extends AbstractChangesRule {

    public AddNameLast() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player) {
        if (section.getString("add-name-last") == null) {
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CommonUtil.getItemName(item) + TextUtil.parse(section.getString("add-name-last"), player));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return 22;
    }
}

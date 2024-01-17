package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetName extends AbstractChangesRule {

    public SetName() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player) {
        if (section.getString("set-name") == null) {
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(TextUtil.parse(section.getString("set-name"), player));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return 7;
    }
}

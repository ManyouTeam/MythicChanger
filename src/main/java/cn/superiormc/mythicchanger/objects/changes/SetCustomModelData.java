package cn.superiormc.mythicchanger.objects.changes;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetCustomModelData extends AbstractChangesRule {

    public SetCustomModelData() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player) {
        if (section.getInt("set-custom-model-data", -1) < 0) {
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(section.getInt("set-custom-model-data"));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return 5;
    }
}

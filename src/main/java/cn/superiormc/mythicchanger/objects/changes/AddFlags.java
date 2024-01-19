package cn.superiormc.mythicchanger.objects.changes;

import com.google.common.base.Enums;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AddFlags extends AbstractChangesRule {

    public AddFlags() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player) {
        if (section.getStringList("add-flags").isEmpty()) {
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        for (String flag : section.getStringList("add-flags")) {
            flag = flag.toUpperCase();
            ItemFlag itemFlag = Enums.getIfPresent(ItemFlag.class, flag).orNull();
            if (itemFlag != null) {
                meta.addItemFlags(itemFlag);
            }
        }
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return 3;
    }
}

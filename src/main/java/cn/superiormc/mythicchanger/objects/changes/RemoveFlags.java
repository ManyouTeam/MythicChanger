package cn.superiormc.mythicchanger.objects.changes;

import com.google.common.base.Enums;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RemoveFlags extends AbstractChangesRule {

    public RemoveFlags() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack item) {
        if (!item.hasItemMeta()) {
            return item;
        }
        if (section.getStringList("remove-flags").isEmpty()) {
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        for (String flag : section.getStringList("remove-flags")) {
            flag = flag.toUpperCase();
            ItemFlag itemFlag = Enums.getIfPresent(ItemFlag.class, flag).orNull();
            if (itemFlag != null) {
                meta.removeItemFlags(itemFlag);
            }
        }
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return -196;
    }
}

package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import com.google.common.base.Enums;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RemoveFlags extends AbstractChangesRule {

    public RemoveFlags() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section,
                               ItemStack original,
                               ItemStack item,
                               Player player,
                               boolean fakeOrReal,
                               boolean isPlayerInventory) {
        if (!item.hasItemMeta()) {
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
        return ConfigManager.configManager.getRuleWeight("remove-flags", -196);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("remove-flags").isEmpty();
    }
}

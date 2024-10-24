package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectApplyItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Deapply extends AbstractChangesRule {

    public Deapply() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section,
                               ItemStack original,
                               ItemStack item,
                               Player player,
                               boolean fakeOrReal,
                               boolean isPlayerInventory) {
        if (section.getBoolean("deapply")) {
            ItemMeta meta = item.getItemMeta();
            meta.getPersistentDataContainer().remove(ObjectApplyItem.MYTHICCHANGER_APPLY_RULE);
            item.setItemMeta(meta);
        }
        return item;
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("deapply", 61);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("deapply") == null;
    }
}

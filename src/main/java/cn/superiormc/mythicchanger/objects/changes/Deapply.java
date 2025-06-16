package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectApplyItem;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Deapply extends AbstractChangesRule {

    public Deapply() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (singleChange.getBoolean("deapply")) {
            ItemMeta meta = singleChange.getItemMeta();
            meta.getPersistentDataContainer().remove(ObjectApplyItem.MYTHICCHANGER_APPLY_RULE);
            return singleChange.setItemMeta(meta);
        }
        return singleChange.getItem();
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("deapply", 61);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("deapply");
    }
}

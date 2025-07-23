package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.objects.ObjectApplyItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class HasApply extends AbstractMatchItemRule {
    public HasApply() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        if (section.getBoolean("has-apply")) {
            return meta.getPersistentDataContainer().has(
                    ObjectApplyItem.MYTHICCHANGER_APPLY_RULE, PersistentDataType.STRING);
        } else {
            return !meta.getPersistentDataContainer().has(
                    ObjectApplyItem.MYTHICCHANGER_APPLY_RULE, PersistentDataType.STRING);
        }
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("has-apply") == null;
    }
}

package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.objects.ObjectApplyItem;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ResetApplyLimit extends AbstractChangesRule {

    public ResetApplyLimit() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (singleChange.getBoolean("reset-apply-limit")) {
            ItemMeta meta = singleChange.getItemMeta();
            meta.getPersistentDataContainer().remove(ObjectApplyItem.MYTHICCHANGER_APPLY_LIMIT);
            return singleChange.setItemMeta(meta);
        }
        return singleChange.getItem();
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.contains("reset-apply-limit");
    }
}

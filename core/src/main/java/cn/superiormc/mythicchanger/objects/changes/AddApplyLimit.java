package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.objects.ObjectApplyItem;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class AddApplyLimit extends AbstractChangesRule {

    public AddApplyLimit() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ItemMeta meta = singleChange.getItemMeta();
        int amount = singleChange.getInt("add-apply-limit") + ObjectApplyItem.getLimit(meta);
        if (amount < 0) {
            amount = 0;
        }
        meta.getPersistentDataContainer().set(ObjectApplyItem.MYTHICCHANGER_APPLY_LIMIT, PersistentDataType.INTEGER, amount);
        return singleChange.setItemMeta(meta);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("add-apply-limit");
    }
}

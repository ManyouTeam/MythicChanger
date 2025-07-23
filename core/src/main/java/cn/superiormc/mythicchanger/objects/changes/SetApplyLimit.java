package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.objects.ObjectApplyItem;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SetApplyLimit extends AbstractChangesRule {

    public SetApplyLimit() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        int amount = singleChange.getInt("set-apply-limit");
        ItemMeta meta = singleChange.getItemMeta();
        meta.getPersistentDataContainer().set(ObjectApplyItem.MYTHICCHANGER_APPLY_LIMIT, PersistentDataType.INTEGER, amount);
        return singleChange.setItemMeta(meta);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("set-apply-limit");
    }
}

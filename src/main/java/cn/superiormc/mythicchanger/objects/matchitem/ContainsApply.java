package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.objects.ObjectApplyItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ContainsApply extends AbstractMatchItemRule {
    public ContainsApply() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        if (!meta.getPersistentDataContainer().has(
                ObjectApplyItem.MYTHICCHANGER_APPLY_RULE, PersistentDataType.STRING)) {
            return false;
        }
        String id = meta.getPersistentDataContainer().get(ObjectApplyItem.MYTHICCHANGER_APPLY_RULE, PersistentDataType.STRING);
        for (String requiredLore : section.getStringList("contains-apply")) {
            if (requiredLore.equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("contains-apply") == null;
    }
}

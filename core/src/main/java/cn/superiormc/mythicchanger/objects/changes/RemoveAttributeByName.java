package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.AttributeUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RemoveAttributeByName extends AbstractChangesRule {

    public RemoveAttributeByName() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ItemMeta meta = singleChange.getItemMeta();
        AttributeUtil.removeCustomAttributeModifiersByName(meta, singleChange.getItem(),
                singleChange.getStringList("remove-attribute-by-name"), false);
        return singleChange.setItemMeta(meta);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("remove-attribute-by-name").isEmpty();
    }
}

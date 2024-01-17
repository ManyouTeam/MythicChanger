package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.utils.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class Replace extends AbstractChangesRule {

    public Replace() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack item) {
        if (section.getConfigurationSection("replace-items") == null) {
            return item;
        }
        return ItemUtil.buildItemStack(section.getConfigurationSection("replace-item"));
    }

    @Override
    public int getWeight() {
        return 1;
    }
}

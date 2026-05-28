package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractChangesRule {

    public AbstractChangesRule() {
        // Empty...
    }

    public abstract ItemStack setChange(ObjectSingleChange singleChange);

    @Deprecated
    public boolean getNeedRewriteItem(ConfigurationSection section) {
        return false;
    }

    public abstract boolean configNotContains(ConfigurationSection section);

    @Override
    public String toString() {
        return getClass().getName();
    }

}

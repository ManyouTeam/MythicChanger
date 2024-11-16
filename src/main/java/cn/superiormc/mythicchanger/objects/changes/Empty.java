package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class Empty extends AbstractChangesRule {

    public Empty() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        return singleChange.getItem();
    }

    @Override
    public int getWeight() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("empty");
    }
}

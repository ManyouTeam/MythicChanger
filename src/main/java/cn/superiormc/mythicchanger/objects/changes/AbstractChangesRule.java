package cn.superiormc.mythicchanger.objects.changes;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractChangesRule implements Comparable<AbstractChangesRule> {

    public AbstractChangesRule() {
        // Empty...
    }

    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player) {
        return item;
    }

    public int getWeight() {
        return 0;
    }

    public boolean getNeedRewriteItem() {
        return false;
    }

    @Override
    public int compareTo(AbstractChangesRule otherRule) {
        if (getWeight() == otherRule.getWeight()) {
            return 1;
        }
        return getWeight() - otherRule.getWeight();
    }

    @Override
    public String toString() {
        return getClass().getName();
    }

}

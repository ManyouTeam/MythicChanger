package cn.superiormc.mythicchanger.objects.changes;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractChangesRule implements Comparable<AbstractChangesRule> {

    public AbstractChangesRule() {
        // Empty...
    }

    public abstract ItemStack setChange(ConfigurationSection section, ItemStack original, ItemStack item, Player player, boolean fakeOrReal);

    public int getWeight() {
        return 0;
    }

    public boolean getNeedRewriteItem(ConfigurationSection section) {
        return false;
    }

    public abstract boolean configNotContains(ConfigurationSection section);

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

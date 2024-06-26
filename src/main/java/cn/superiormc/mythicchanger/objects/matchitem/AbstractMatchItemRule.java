package cn.superiormc.mythicchanger.objects.matchitem;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;


public abstract class AbstractMatchItemRule {

    public AbstractMatchItemRule() {
        // Empty...
    }

    public abstract boolean getMatch(ConfigurationSection section, ItemStack item);

    public abstract boolean configNotContains(ConfigurationSection section);

    @Override
    public String toString() {
        return getClass().getName();
    }
}

package cn.superiormc.mythicchanger.objects.matchitem;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public abstract class AbstractMatchItemRule {

    public AbstractMatchItemRule() {
        // Empty...
    }

    @Deprecated
    public boolean getMatch(ConfigurationSection section, ItemStack item) {
        return getMatch(section, item, item.getItemMeta());
    }
    
    public abstract boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta);

    public abstract boolean configNotContains(ConfigurationSection section);

    @Override
    public String toString() {
        return getClass().getName();
    }
}

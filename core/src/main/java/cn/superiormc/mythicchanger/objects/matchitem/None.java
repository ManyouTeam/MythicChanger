package cn.superiormc.mythicchanger.objects.matchitem;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class None extends AbstractMatchItemRule {

    public None() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        return false;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.getBoolean("none", false);
    }
}

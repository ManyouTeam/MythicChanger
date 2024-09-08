package cn.superiormc.mythicchanger.objects.matchitem;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HasLore extends AbstractMatchItemRule {
    public HasLore() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        if (section.getBoolean("has-lore", false)) {
            return meta.hasLore();
        } else {
            return !meta.hasLore();
        }
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("has-lore") == null;
    }
}

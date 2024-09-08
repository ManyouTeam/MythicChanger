package cn.superiormc.mythicchanger.objects.matchitem;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HasName extends AbstractMatchItemRule {
    public HasName() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        if (section.getBoolean("has-name", false)) {
            return meta.hasDisplayName();
        } else {
            return !meta.hasDisplayName();
        }
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("has-name") == null;
    }
}

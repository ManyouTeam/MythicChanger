package cn.superiormc.mythicchanger.objects.matchitem;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class HasName extends AbstractMatchItemRule {
    public HasName() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item) {
        if (section.getBoolean("has-name", false)) {
            return item.getItemMeta() != null && item.getItemMeta().hasDisplayName();
        } else {
            return item.getItemMeta() != null && !item.getItemMeta().hasDisplayName();
        }
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("has-name") == null;
    }
}

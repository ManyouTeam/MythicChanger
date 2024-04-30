package cn.superiormc.mythicchanger.objects.matchitem;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class HasLore extends AbstractMatchItemRule {
    public HasLore() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item) {
        if (item.getItemMeta() == null) {
            return false;
        }
        if (section.getBoolean("has-lore", false)) {
            return item.getItemMeta().hasLore();
        } else {
            return !item.getItemMeta().hasLore();
        }
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("has-lore") == null;
    }
}

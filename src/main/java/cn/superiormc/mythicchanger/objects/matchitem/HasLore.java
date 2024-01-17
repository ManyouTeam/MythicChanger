package cn.superiormc.mythicchanger.objects.matchitem;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class HasLore extends AbstractMatchItemRule {
    public HasLore() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item) {
        if (item == null) {
            return false;
        }
        if (section.get("has-lore") == null) {
            return true;
        } if (section.getBoolean("has-lore", false)) {
            return item.hasItemMeta() && item.getItemMeta().hasLore();
        } else {
            return item.hasItemMeta() && !item.getItemMeta().hasLore();
        }
    }
}

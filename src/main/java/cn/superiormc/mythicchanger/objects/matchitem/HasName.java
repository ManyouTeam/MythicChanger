package cn.superiormc.mythicchanger.objects.matchitem;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class HasName extends AbstractMatchItemRule {
    public HasName() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item) {
        if (item == null) {
            return false;
        }
        if (section.get("has-name") == null) {
            return true;
        } if (section.getBoolean("has-name", false)) {
            return item.hasItemMeta() && item.getItemMeta().hasDisplayName();
        } else {
            return item.hasItemMeta() && !item.getItemMeta().hasDisplayName();
        }
    }
}

package cn.superiormc.mythicchanger.objects.matchitem;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ContainsName extends AbstractMatchItemRule{
    public ContainsName() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item) {
        if (section.getStringList("contains-name").isEmpty()) {
            return true;
        }
        if (item == null || !item.hasItemMeta()) {
            return false;
        }
        if (!item.getItemMeta().hasDisplayName()) {
            return false;
        }
        for (String requiredName : section.getStringList("contains-name")) {
            if (item.getItemMeta().getDisplayName().contains(requiredName)) {
                return true;
            }
        }
        return false;
    }
}

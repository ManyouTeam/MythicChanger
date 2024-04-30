package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.hooks.CheckValidHook;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class Items extends AbstractMatchItemRule {
    public Items() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item) {
        return section.getStringList("items").contains(
                CheckValidHook.checkValid(item, section.getBoolean("use-tier-identify", false)));
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("items").isEmpty();
    }
}

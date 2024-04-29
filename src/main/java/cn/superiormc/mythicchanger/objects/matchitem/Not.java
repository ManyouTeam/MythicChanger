package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.manager.MatchItemManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class Not extends AbstractMatchItemRule{
    public Not() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item) {
        if (item == null) {
            return false;
        }
        ConfigurationSection notSection = section.getConfigurationSection("not");
        if (notSection == null) {
            return true;
        }
        for (AbstractMatchItemRule rule : MatchItemManager.matchItemManager.getRules()) {
            if (!rule.getMatch(notSection, item)) {
                return true;
            }
        }
        return false;
    }
}

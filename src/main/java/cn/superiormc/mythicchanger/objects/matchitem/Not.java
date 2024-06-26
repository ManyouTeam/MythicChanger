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
        ConfigurationSection notSection = section.getConfigurationSection("not");
        for (AbstractMatchItemRule rule : MatchItemManager.matchItemManager.getRules()) {
            if (rule.configNotContains(notSection)) {
                continue;
            }
            if (rule.getMatch(notSection, item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("not") == null;
    }
}

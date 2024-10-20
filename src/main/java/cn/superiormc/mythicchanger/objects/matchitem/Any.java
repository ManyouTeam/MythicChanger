package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.manager.MatchItemManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Any extends AbstractMatchItemRule {
    public Any() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        ConfigurationSection anySection = section.getConfigurationSection("any");
        for (AbstractMatchItemRule rule : MatchItemManager.matchItemManager.getRules()) {
            if (rule.configNotContains(anySection)) {
                continue;
            }
            if (rule.getMatch(anySection, item, meta)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("any") == null;
    }
}

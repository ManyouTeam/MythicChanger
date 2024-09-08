package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.manager.MatchItemManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Not extends AbstractMatchItemRule{
    public Not() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        ConfigurationSection notSection = section.getConfigurationSection("not");
        for (AbstractMatchItemRule rule : MatchItemManager.matchItemManager.getRules()) {
            if (rule.configNotContains(notSection)) {
                continue;
            }
            if (rule.getMatch(notSection, item, meta)) {
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

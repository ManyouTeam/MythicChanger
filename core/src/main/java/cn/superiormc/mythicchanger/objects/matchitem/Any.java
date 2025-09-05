package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.manager.MatchItemManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Set;

public class Any extends AbstractMatchItemRule {

    public Any() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        ConfigurationSection anySection = section.getConfigurationSection("any");
        Set<String> anyKeys = anySection.getKeys(false);
        if (anyKeys.isEmpty()) {
            return true;
        }
        if (anyKeys.contains("1")) {
            big: for (String anyKey : anyKeys) {
                ConfigurationSection checkSection = anySection.getConfigurationSection(anyKey);
                for (AbstractMatchItemRule rule : MatchItemManager.matchItemManager.getRules()) {
                    if (rule.configNotContains(checkSection)) {
                        continue;
                    }
                    if (!rule.getMatch(checkSection, item, meta)) {
                        continue big;
                    }
                }
                return true;
            }
        } else {
            for (AbstractMatchItemRule rule : MatchItemManager.matchItemManager.getRules()) {
                if (rule.configNotContains(anySection)) {
                    continue;
                }
                if (rule.getMatch(anySection, item, meta)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("any");
    }
}

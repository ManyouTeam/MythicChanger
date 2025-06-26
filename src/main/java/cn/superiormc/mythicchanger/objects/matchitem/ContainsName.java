package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ContainsName extends AbstractMatchItemRule {
    public ContainsName() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        if (!meta.hasDisplayName()) {
            return false;
        }
        for (String requiredName : section.getStringList("contains-name")) {
            if (ConfigManager.configManager.getBoolean("ignore-color-code")) {
                if (TextUtil.clear(meta.getDisplayName()).contains(requiredName)) {
                    return true;
                }
            } else {
                if (meta.getDisplayName().contains(TextUtil.parse(requiredName))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("contains-name").isEmpty();
    }
}

package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ContainsLore extends AbstractMatchItemRule {
    public ContainsLore() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        if (!meta.hasLore()) {
            return false;
        }
        for (String hasLore : meta.getLore()) {
            for (String requiredLore : section.getStringList("contains-lore")) {
                if (ConfigManager.configManager.getBoolean("ignore-color-code")) {
                    if (TextUtil.clear(hasLore).contains(requiredLore)) {
                        return true;
                    }
                } else {
                    if (hasLore.contains(TextUtil.parse(requiredLore))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("contains-lore").isEmpty();
    }
}

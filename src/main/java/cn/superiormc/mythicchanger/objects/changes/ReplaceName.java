package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ReplaceName extends AbstractChangesRule {

    public ReplaceName() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section,
                               ItemStack original,
                               ItemStack item,
                               Player player,
                               boolean fakeOrReal,
                               boolean isPlayerInventory) {
        ConfigurationSection tempVal1 = section.getConfigurationSection("replace-name");
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            return item;
        }
        String displayName = meta.getDisplayName();
        for (String requiredName : tempVal1.getKeys(false)) {
            displayName = displayName.replace(requiredName, TextUtil.parse(tempVal1.getString(requiredName), player));
        }
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("replace-name", 8);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("replace-name") == null;
    }
}
package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ReplaceName extends AbstractChangesRule {

    public ReplaceName() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ConfigurationSection tempVal1 = singleChange.getConfigurationSection("replace-name");
        ItemMeta meta = singleChange.getItemMeta();
        if (!meta.hasLore()) {
            return singleChange.getItem();
        }
        String displayName = MythicChanger.methodUtil.getItemName(meta);
        for (String requiredName : tempVal1.getKeys(false)) {
            displayName = displayName.replace(requiredName, tempVal1.getString(requiredName, ""));
        }
        MythicChanger.methodUtil.setItemName(meta, displayName, singleChange.getPlayer());
        return singleChange.setItemMeta(meta);
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

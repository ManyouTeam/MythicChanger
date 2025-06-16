package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KeepItemName extends AbstractChangesRule {

    public KeepItemName() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (singleChange.getBoolean("keep-item-name")) {
            ItemMeta meta = singleChange.getItemMeta();
            ItemMeta originalMeta = singleChange.getOriginalMeta();
            if (originalMeta.hasItemName()) {
                meta.setItemName(originalMeta.getItemName());
            }
            return singleChange.setItemMeta(meta);
        }
        return singleChange.getItem();
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("keep-item-name", -253);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("keep-item-name") && CommonUtil.getMinorVersion(20, 5);
    }
}

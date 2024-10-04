package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KeepItemName extends AbstractChangesRule {

    public KeepItemName() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section,
                               ItemStack original,
                               ItemStack item,
                               Player player,
                               boolean fakeOrReal,
                               boolean isPlayerInventory) {
        if (section.getBoolean("keep-item-name", false)) {
            ItemMeta meta = item.getItemMeta();
            ItemMeta originalMeta = original.getItemMeta();
            if (meta == null || originalMeta == null) {
                return item;
            }
            if (originalMeta.hasItemName()) {
                meta.setItemName(originalMeta.getItemName());
            }
            item.setItemMeta(meta);
            return item;
        }
        return item;
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("keep-item-name", -253);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("keep-item-name") == null && CommonUtil.getMinorVersion(20, 5);
    }
}

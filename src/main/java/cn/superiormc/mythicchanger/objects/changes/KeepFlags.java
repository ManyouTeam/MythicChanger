package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KeepFlags extends AbstractChangesRule {

    public KeepFlags() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section,
                               ItemStack original,
                               ItemStack item,
                               Player player,
                               boolean fakeOrReal,
                               boolean isPlayerInventory) {
        if (section.getBoolean("keep-flags", false)) {
            ItemMeta meta = item.getItemMeta();
            ItemMeta originalMeta = original.getItemMeta();
            if (meta == null || originalMeta == null) {
                return item;
            }
            if (!originalMeta.getItemFlags().isEmpty()) {
                for (ItemFlag flag : originalMeta.getItemFlags()) {
                    meta.addItemFlags(flag);
                }
            }
            item.setItemMeta(meta);
            return item;
        }
        return item;
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("keep-flags", -252);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("keep-flags") == null;
    }
}

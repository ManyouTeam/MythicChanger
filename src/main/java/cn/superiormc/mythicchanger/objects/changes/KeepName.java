package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class KeepName extends AbstractChangesRule {

    public KeepName() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack original, ItemStack item, Player player, boolean fakeOrReal) {
        if (section.getBoolean("keep-name", false)) {
            ItemMeta meta = item.getItemMeta();
            ItemMeta originalMeta = original.getItemMeta();
            if (meta == null || originalMeta == null) {
                return item;
            }
            if (originalMeta.hasDisplayName()) {
                meta.setDisplayName(originalMeta.getDisplayName());
            }
            item.setItemMeta(meta);
            return item;
        }
        return item;
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("keep-name", -255);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("keep-name") == null;
    }
}

package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class KeepDamage extends AbstractChangesRule {

    public KeepDamage() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack original, ItemStack item, Player player, boolean fakeOrReal) {
        if (section.getBoolean("keep-damage", false)) {
            ItemMeta meta = item.getItemMeta();
            ItemMeta originalMeta = original.getItemMeta();
            if (meta == null || originalMeta == null) {
                return item;
            }
            if (meta instanceof Damageable && originalMeta instanceof Damageable) {
                Damageable damageable = (Damageable) meta;
                Damageable originalDamageable = (Damageable) originalMeta;
                if (originalDamageable.hasDamage()) {
                    damageable.setDamage(originalDamageable.getDamage());
                }
                item.setItemMeta(damageable);
            }
            return item;
        }
        return item;
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("keep-damage", -250);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("keep-damage") == null;
    }
}

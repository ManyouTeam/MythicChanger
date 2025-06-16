package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class KeepDamage extends AbstractChangesRule {

    public KeepDamage() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (singleChange.getBoolean("keep-damage")) {
            ItemMeta meta = singleChange.getItemMeta();
            ItemMeta originalMeta = singleChange.getOriginalMeta();
            if (meta == null || originalMeta == null) {
                return singleChange.getItem();
            }
            if (meta instanceof Damageable && originalMeta instanceof Damageable) {
                Damageable damageable = (Damageable) meta;
                Damageable originalDamageable = (Damageable) originalMeta;
                if (originalDamageable.hasDamage()) {
                    damageable.setDamage(originalDamageable.getDamage());
                }
                return singleChange.setItemMeta(damageable);
            }
            return singleChange.getItem();
        }
        return singleChange.getItem();
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("keep-damage", -250);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("keep-damage");
    }
}

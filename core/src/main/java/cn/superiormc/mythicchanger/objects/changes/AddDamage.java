package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public final class AddDamage extends AbstractChangesRule {

    @Override
    public ItemStack setChange(ObjectSingleChange change) {
        if (!(change.getItemMeta() instanceof Damageable damageable)) {
            return change.getItem();
        }
        damageable.setDamage(Math.max(0, Math.min(change.getItem().getType().getMaxDurability(),
                damageable.getDamage() + change.getInt("add-damage", 0))));
        return change.setItemMeta(damageable);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("add-damage");
    }
}

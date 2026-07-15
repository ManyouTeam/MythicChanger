package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Locale;

public final class Damage extends AbstractChangesRule {

    @Override
    public ItemStack setChange(ObjectSingleChange change) {
        if (!(change.getItemMeta() instanceof Damageable damageable)) {
            return change.getItem();
        }
        ConfigurationSection rule = change.getConfigurationSection("damage");
        double operand = rule == null ? change.getDouble("damage", damageable.getDamage())
                : change.getDouble("damage.value", damageable.getDamage());
        String operation = rule == null ? "SET" : rule.getString("operation", "SET").toUpperCase(Locale.ROOT);
        double result = switch (operation) {
            case "ADD" -> damageable.getDamage() + operand;
            case "SUBTRACT" -> damageable.getDamage() - operand;
            case "MULTIPLY" -> damageable.getDamage() * operand;
            default -> operand;
        };
        damageable.setDamage(Math.max(0, Math.min(change.getItem().getType().getMaxDurability(), (int) Math.round(result))));
        return change.setItemMeta(damageable);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("damage");
    }
}

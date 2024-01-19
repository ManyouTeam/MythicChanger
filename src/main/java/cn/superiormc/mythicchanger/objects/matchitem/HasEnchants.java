package cn.superiormc.mythicchanger.objects.matchitem;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HasEnchants extends AbstractMatchItemRule {
    public HasEnchants() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item) {
        if (item == null) {
            return false;
        }
        if (section.getStringList("has-enchants").isEmpty()) {
            return true;
        }
        ItemMeta meta = item.getItemMeta();
        for (String ench : section.getStringList("has-enchants")) {
            Enchantment vanillaEnchant = Enchantment.getByKey(NamespacedKey.minecraft(ench.toLowerCase()));
            if (vanillaEnchant != null && meta.getEnchants().containsKey(vanillaEnchant)) {
                return true;
            }
        }
        return false;
    }
}

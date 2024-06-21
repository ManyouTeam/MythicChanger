package cn.superiormc.mythicchanger.objects.changes;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ReplaceEnchants extends AbstractChangesRule {

    public ReplaceEnchants() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack original, ItemStack item, Player player, boolean fakeOrReal) {
        if (!item.hasItemMeta()) {
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        ConfigurationSection enchantSection = section.getConfigurationSection("replace-enchants");
        for (String ench : enchantSection.getKeys(false)) {
            Enchantment vanillaEnchant = Enchantment.getByKey(NamespacedKey.minecraft(ench.toLowerCase()));
            if (vanillaEnchant != null && item.getEnchantments().get(vanillaEnchant) != null) {
                int level = item.getEnchantmentLevel(vanillaEnchant);
                meta.removeEnchant(vanillaEnchant);
                Enchantment addEnchant = Enchantment.getByKey(NamespacedKey.minecraft(enchantSection.getString(ench).toLowerCase()));
                if (addEnchant != null) {
                    meta.addEnchant(addEnchant, level, false);
                }
            }
        }
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return -203;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("replace-enchants") == null;
    }
}

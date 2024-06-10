package cn.superiormc.mythicchanger.objects.changes;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DeleteEnchants extends AbstractChangesRule {

    public DeleteEnchants() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack original, ItemStack item, Player player, boolean fakeOrReal) {
        if (!item.hasItemMeta()) {
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        for (String ench : section.getConfigurationSection("delete-enchants").getKeys(false)) {
            Enchantment vanillaEnchant = Enchantment.getByKey(NamespacedKey.minecraft(ench.toLowerCase()));
            if (vanillaEnchant != null && item.getEnchantments().get(vanillaEnchant) != null &&
            item.getEnchantments().get(vanillaEnchant) > section.getConfigurationSection("delete-enchants").getInt(ench)) {
                meta.removeEnchant(vanillaEnchant);
            }
        }
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return -199;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("delete-enchants") == null;
    }
}

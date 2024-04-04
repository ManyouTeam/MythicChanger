package cn.superiormc.mythicchanger.objects.changes;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RemoveEnchants extends AbstractChangesRule {

    public RemoveEnchants() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player, boolean fakeOrReal) {
        if (!item.hasItemMeta()) {
            return item;
        }
        if (section.getStringList("remove-enchants").isEmpty()) {
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        for (String ench : section.getStringList("remove-enchants")) {
            Enchantment vanillaEnchant = Enchantment.getByKey(NamespacedKey.minecraft(ench.toLowerCase()));
            if (vanillaEnchant != null) {
                meta.removeEnchant(vanillaEnchant);
            }
        }
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return -197;
    }
}

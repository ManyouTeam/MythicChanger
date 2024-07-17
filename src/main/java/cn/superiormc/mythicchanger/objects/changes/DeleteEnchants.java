package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.MythicChanger;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DeleteEnchants extends AbstractChangesRule {

    public DeleteEnchants() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack original, ItemStack item, Player player, boolean fakeOrReal) {
        if (!item.hasItemMeta()) {
            return item;
        }
        ConfigurationSection deleteEnchantsSection = section.getConfigurationSection("delete-enchants");
        ItemMeta meta = item.getItemMeta();
        for (String ench : deleteEnchantsSection.getKeys(false)) {
            Enchantment vanillaEnchant = Enchantment.getByKey(NamespacedKey.minecraft(ench.toLowerCase()));
            if (vanillaEnchant == null || item.getEnchantments().get(vanillaEnchant) == null) {
                continue;
            }
            if (!MythicChanger.freeVersion && deleteEnchantsSection.getString(ench).startsWith("[")) {
                if (deleteEnchantsSection.getIntegerList(ench).contains(item.getEnchantments().get(vanillaEnchant))) {
                    meta.removeEnchant(vanillaEnchant);
                }
            } else {
                if (item.getEnchantments().get(vanillaEnchant) > section.getInt(ench)) {
                    meta.removeEnchant(vanillaEnchant);
                }
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

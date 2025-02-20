package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class HasEnchants extends AbstractMatchItemRule {
    public HasEnchants() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        for (String ench : section.getStringList("has-enchants")) {
            if (!MythicChanger.freeVersion && ench.equals("*")) {
                return !meta.getEnchants().isEmpty();
            }
            Enchantment vanillaEnchant = Registry.ENCHANTMENT.get(CommonUtil.parseNamespacedKey(ench.toLowerCase()));
            if (vanillaEnchant != null) {
                if (meta instanceof EnchantmentStorageMeta) {
                    EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) meta;
                    return enchantmentStorageMeta.getStoredEnchants().containsKey(vanillaEnchant);
                } else {
                    return meta.getEnchants().containsKey(vanillaEnchant);
                }
            }
        }
        return false;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("has-enchants").isEmpty();
    }
}

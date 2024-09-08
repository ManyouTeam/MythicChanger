package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ContainsEnchants extends AbstractMatchItemRule {
    public ContainsEnchants() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        ConfigurationSection containsEnchantsSection = section.getConfigurationSection("contains-enchants");
        for (String ench : containsEnchantsSection.getKeys(false)) {
            Enchantment vanillaEnchant = Registry.ENCHANTMENT.get(CommonUtil.parseNamespacedKey(ench.toLowerCase()));
            if (vanillaEnchant == null || item.getEnchantments().get(vanillaEnchant) == null) {
                continue;
            }
            if (containsEnchantsSection.getString(ench).startsWith("[")) {
                return containsEnchantsSection.getIntegerList(ench).contains(item.getEnchantments().get(vanillaEnchant));
            } else {
                return item.getEnchantments().get(vanillaEnchant) > section.getInt(ench);
            }
        }
        return false;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("contains-enchants");
    }
}

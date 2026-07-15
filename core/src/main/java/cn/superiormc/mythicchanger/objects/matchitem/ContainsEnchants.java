package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class ContainsEnchants extends AbstractMatchItemRule {

    public ContainsEnchants() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, Player player, ItemStack item, ItemMeta meta) {
        ConfigurationSection containsEnchantsSection = section.getConfigurationSection("contains-enchants");
        for (Map.Entry<String, Object> entry : containsEnchantsSection.getValues(true).entrySet()) {
            if (entry.getValue() instanceof ConfigurationSection) {
                continue;
            }
            String ench = entry.getKey();
            String parsedEnchant = CommonUtil.parseLang(player, ench);
            Enchantment vanillaEnchant = Registry.ENCHANTMENT.get(CommonUtil.parseNamespacedKey(parsedEnchant.toLowerCase()));
            if (vanillaEnchant == null) {
                continue;
            }
            int level;
            if (meta instanceof EnchantmentStorageMeta) {
                EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) meta;
                level = enchantmentStorageMeta.getStoredEnchantLevel(vanillaEnchant);
            } else {
                level = meta.getEnchantLevel(vanillaEnchant);
            }
            String value = containsEnchantsSection.getString(ench, "");
            if (CommonUtil.parseLang(player, value).startsWith("[")) {
                return containsEnchantsSection.getIntegerList(ench).contains(level);
            } else {
                return level > containsEnchantsSection.getInt(ench);
            }
        }
        return false;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("contains-enchants");
    }
}

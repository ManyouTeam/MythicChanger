package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class HasStoredEnchants extends AbstractMatchItemRule {

    public HasStoredEnchants() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        if (!(meta instanceof EnchantmentStorageMeta)) {
            return false; // 不是附魔书直接返回 false
        }

        EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) meta;

        for (String ench : section.getStringList("has-stored-enchants")) {
            if (!MythicChanger.freeVersion && ench.equals("*")) {
                return !storageMeta.getStoredEnchants().isEmpty();
            }
            Enchantment vanillaEnchant = Registry.ENCHANTMENT.get(CommonUtil.parseNamespacedKey(ench.toLowerCase()));
            if (vanillaEnchant != null && storageMeta.getStoredEnchants().containsKey(vanillaEnchant)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("has-stored-enchants").isEmpty();
    }
}
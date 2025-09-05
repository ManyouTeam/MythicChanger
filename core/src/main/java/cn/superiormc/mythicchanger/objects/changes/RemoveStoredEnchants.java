package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class RemoveStoredEnchants extends AbstractChangesRule {

    public RemoveStoredEnchants() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ItemMeta meta = singleChange.getItemMeta();

        // 检查是否是附魔书
        if (!(meta instanceof EnchantmentStorageMeta)) {
            return singleChange.getItem(); // 不是附魔书就直接返回
        }

        EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) meta;

        for (String ench : singleChange.getStringList("remove-stored-enchants")) {
            Enchantment vanillaEnchant = Registry.ENCHANTMENT.get(CommonUtil.parseNamespacedKey(ench.toLowerCase()));
            if (vanillaEnchant != null && storageMeta.hasStoredEnchant(vanillaEnchant)) {
                storageMeta.removeStoredEnchant(vanillaEnchant);
            }
        }

        return singleChange.setItemMeta(storageMeta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("remove-stored-enchants", -197);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("remove-stored-enchants").isEmpty();
    }
}
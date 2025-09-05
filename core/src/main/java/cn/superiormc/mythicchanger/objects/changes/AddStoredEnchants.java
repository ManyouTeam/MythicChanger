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

public class AddStoredEnchants extends AbstractChangesRule {

    public AddStoredEnchants() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ItemMeta meta = singleChange.getItemMeta();

        // 确认物品是附魔书并且 meta 是 EnchantmentStorageMeta
        if (!(meta instanceof EnchantmentStorageMeta)) {
            return singleChange.getItem(); // 不是附魔书则不处理
        }

        EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) meta;

        for (String ench : singleChange.getConfigurationSection("add-stored-enchants").getKeys(false)) {
            Enchantment vanillaEnchant = Registry.ENCHANTMENT.get(CommonUtil.parseNamespacedKey(ench));
            if (vanillaEnchant != null) {
                int level = singleChange.getInt("add-stored-enchants." + ench);

                // 获取已有附魔等级
                Integer currentLevel = storageMeta.getStoredEnchants().get(vanillaEnchant);

                if (currentLevel == null || (currentLevel < level ||
                        singleChange.getBoolean("add-stored-enchants-ignore-level"))) {
                    storageMeta.addStoredEnchant(vanillaEnchant, level, true);
                }
            }
        }
        return singleChange.setItemMeta(storageMeta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("add-stored-enchants", 2);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("add-stored-enchants") == null;
    }
}
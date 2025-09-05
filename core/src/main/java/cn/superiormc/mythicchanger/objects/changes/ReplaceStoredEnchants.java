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

public class ReplaceStoredEnchants extends AbstractChangesRule {

    public ReplaceStoredEnchants() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ItemMeta meta = singleChange.getItemMeta();

        // 必须是附魔书
        if (!(meta instanceof EnchantmentStorageMeta)) {
            return singleChange.getItem();
        }

        EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) meta;
        ConfigurationSection enchantSection = singleChange.getConfigurationSection("replace-stored-enchants");
        if (enchantSection == null) {
            return singleChange.getItem();
        }

        for (String ench : enchantSection.getKeys(false)) {
            Enchantment vanillaEnchant = Registry.ENCHANTMENT.get(CommonUtil.parseNamespacedKey(ench.toLowerCase()));
            if (vanillaEnchant != null && storageMeta.hasStoredEnchant(vanillaEnchant)) {
                int level = storageMeta.getStoredEnchants().get(vanillaEnchant);
                storageMeta.removeStoredEnchant(vanillaEnchant);

                Enchantment addEnchant = Registry.ENCHANTMENT.get(CommonUtil.parseNamespacedKey(enchantSection.getString(ench, ench)));
                if (addEnchant != null) {
                    storageMeta.addStoredEnchant(addEnchant, level, false);
                }
            }
        }

        return singleChange.setItemMeta(storageMeta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("replace-stored-enchants", -203);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("replace-stored-enchants") == null;
    }
}
package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ReplaceEnchants extends AbstractChangesRule {

    public ReplaceEnchants() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ItemMeta meta = singleChange.getItemMeta();
        ConfigurationSection enchantSection = singleChange.section.getConfigurationSection("replace-enchants");
        for (String ench : enchantSection.getKeys(false)) {
            Enchantment vanillaEnchant = Registry.ENCHANTMENT.get(CommonUtil.parseNamespacedKey(ench.toLowerCase()));
            if (vanillaEnchant != null && singleChange.getItem().getEnchantments().get(vanillaEnchant) != null) {
                int level = singleChange.getItem().getEnchantmentLevel(vanillaEnchant);
                meta.removeEnchant(vanillaEnchant);
                Enchantment addEnchant = Registry.ENCHANTMENT.get(CommonUtil.parseNamespacedKey(ench.toLowerCase()));
                if (addEnchant != null) {
                    meta.addEnchant(addEnchant, level, false);
                }
            }
        }
        return singleChange.setItemMeta(meta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("replace-enchants", -203);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("replace-enchants") == null;
    }
}

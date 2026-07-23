package cn.superiormc.mythicchanger.objects.changes;

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
        ConfigurationSection enchantSection = singleChange.getConfigurationSection("replace-enchants");
        if (enchantSection == null) {
            return singleChange.getItem();
        }
        for (String ench : enchantSection.getKeys(false)) {
            Enchantment vanillaEnchant = Registry.ENCHANTMENT.get(CommonUtil.parseNamespacedKey(ench.toLowerCase()));
            if (vanillaEnchant != null && singleChange.getItemMeta().getEnchants().get(vanillaEnchant) != null) {
                int level = singleChange.getItemMeta().getEnchantLevel(vanillaEnchant);
                meta.removeEnchant(vanillaEnchant);
                Enchantment addEnchant = Registry.ENCHANTMENT.get(CommonUtil.parseNamespacedKey(enchantSection.getString(ench, ench)));
                if (addEnchant != null) {
                    meta.addEnchant(addEnchant, level, false);
                }
            }
        }
        return singleChange.setItemMeta(meta);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("replace-enchants") == null;
    }
}

package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class RemoveAllStoredEnchants extends AbstractChangesRule {

    public RemoveAllStoredEnchants() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (!singleChange.getBoolean("remove-all-stored-enchants")) {
            return singleChange.getItem();
        }

        ItemMeta meta = singleChange.getItemMeta();
        if (!(meta instanceof EnchantmentStorageMeta)) {
            return singleChange.getItem(); // 不是附魔书，直接返回
        }

        EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) meta;
        for (Enchantment enchant : storageMeta.getStoredEnchants().keySet()) {
            storageMeta.removeStoredEnchant(enchant);
        }

        return singleChange.setItemMeta(storageMeta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("remove-all-stored-enchants", -198);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("remove-all-stored-enchants");
    }
}
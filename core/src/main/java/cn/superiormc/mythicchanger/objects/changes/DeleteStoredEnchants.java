package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class DeleteStoredEnchants extends AbstractChangesRule {

    public DeleteStoredEnchants() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ConfigurationSection deleteEnchantsSection = singleChange.getConfigurationSection("delete-stored-enchants");
        ItemMeta meta = singleChange.getItemMeta();

        if (!(meta instanceof EnchantmentStorageMeta)) {
            return singleChange.getItem(); // 不是附魔书就直接返回
        }

        EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) meta;

        for (String ench : deleteEnchantsSection.getKeys(false)) {
            Enchantment vanillaEnchant = Registry.ENCHANTMENT.get(CommonUtil.parseNamespacedKey(ench.toLowerCase()));
            if (vanillaEnchant == null || !storageMeta.hasStoredEnchant(vanillaEnchant)) {
                continue;
            }

            int currentLevel = storageMeta.getStoredEnchants().get(vanillaEnchant);

            if (!MythicChanger.freeVersion && deleteEnchantsSection.getString(ench).startsWith("[")) {
                // 多等级匹配
                if (deleteEnchantsSection.getIntegerList(ench).contains(currentLevel)) {
                    storageMeta.removeStoredEnchant(vanillaEnchant);
                }
            } else {
                // 单一等级比较
                if (currentLevel > singleChange.getInt(ench)) {
                    storageMeta.removeStoredEnchant(vanillaEnchant);
                }
            }
        }

        return singleChange.setItemMeta(storageMeta);
    }

    @Override
    public int getWeight() {
        return -199;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("delete-stored-enchants") == null;
    }
}
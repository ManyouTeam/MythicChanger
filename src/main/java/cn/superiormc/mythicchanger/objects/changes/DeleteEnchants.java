package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DeleteEnchants extends AbstractChangesRule {

    public DeleteEnchants() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ConfigurationSection deleteEnchantsSection = singleChange.getConfigurationSection("delete-enchants");
        ItemMeta meta = singleChange.getItemMeta();
        for (String ench : deleteEnchantsSection.getKeys(false)) {
            Enchantment vanillaEnchant = Registry.ENCHANTMENT.get(CommonUtil.parseNamespacedKey(ench.toLowerCase()));
            if (vanillaEnchant == null || singleChange.getItem().getEnchantments().get(vanillaEnchant) == null) {
                continue;
            }
            if (!MythicChanger.freeVersion && deleteEnchantsSection.getString(ench).startsWith("[")) {
                if (deleteEnchantsSection.getIntegerList(ench).contains(singleChange.getItem().getEnchantments().get(vanillaEnchant))) {
                    meta.removeEnchant(vanillaEnchant);
                }
            } else {
                if (singleChange.getItem().getEnchantments().get(vanillaEnchant) > singleChange.getInt(ench)) {
                    meta.removeEnchant(vanillaEnchant);
                }
            }
        }
        return singleChange.setItemMeta(meta);
    }

    @Override
    public int getWeight() {
        return -199;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("delete-enchants") == null;
    }
}

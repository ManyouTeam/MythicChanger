package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class KeepEnchants extends AbstractChangesRule {

    public KeepEnchants() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ItemMeta meta = singleChange.getItemMeta();
        ItemMeta originalMeta = singleChange.getOriginalMeta();
        Map<Enchantment, Integer> enchants = originalMeta.getEnchants();
        if (singleChange.getBoolean("keep-enchants") && !enchants.isEmpty()) {
            for (Enchantment enchantment : enchants.keySet()) {
                meta.addEnchant(enchantment, enchants.get(enchantment), true);
            }
            singleChange.setItemMeta(meta);
            return singleChange.getItem();
        }
        return singleChange.getItem();
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("keep-enchants", -251);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("keep-enchants");
    }
}

package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AddEnchants extends AbstractChangesRule {

    public AddEnchants() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack original, ItemStack item, Player player, boolean fakeOrReal) {
        ItemMeta meta = item.getItemMeta();
        for (String ench : section.getConfigurationSection("add-enchants").getKeys(false)) {
            Enchantment vanillaEnchant = Enchantment.getByKey(NamespacedKey.minecraft(ench.toLowerCase()));
            if (vanillaEnchant != null) {
                int level = section.getConfigurationSection("add-enchants").getInt(ench);
                if (meta.getEnchants().get(vanillaEnchant) == null || meta.getEnchants().get(vanillaEnchant) < level) {
                    meta.addEnchant(vanillaEnchant, level, true);
                }
            }
        }
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("add-enchants", 2);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("add-enchants") == null;
    }
}

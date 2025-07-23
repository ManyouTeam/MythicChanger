package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RemoveAllEnchants extends AbstractChangesRule {

    public RemoveAllEnchants() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (!singleChange.getBoolean("remove-all-enchants")) {
            return singleChange.getItem();
        }
        ItemMeta meta = singleChange.getItemMeta();
        for (Enchantment enchant : singleChange.getItem().getEnchantments().keySet()) {
            meta.removeEnchant(enchant);
        }
        return singleChange.setItemMeta(meta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("remove-all-enchants", -198);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("remove-all-enchants");
    }
}

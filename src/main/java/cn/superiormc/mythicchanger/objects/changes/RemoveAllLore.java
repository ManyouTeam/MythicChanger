package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RemoveAllLore extends AbstractChangesRule {

    public RemoveAllLore() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (!singleChange.getBoolean("remove-all-lore")) {
            return singleChange.getItem();
        }
        ItemMeta meta = singleChange.getItemMeta();
        if (meta.hasLore()) {
            meta.setLore(null);
        }
        singleChange.setItemMeta(meta);
        return singleChange.getItem();
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("remove-all-lore", -301);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("remove-all-lore");
    }
}

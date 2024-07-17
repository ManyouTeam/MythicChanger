package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KeepEnchants extends AbstractChangesRule {

    public KeepEnchants() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack original, ItemStack item, Player player, boolean fakeOrReal) {
        if (section.getBoolean("keep-enchants", false) && !original.getEnchantments().isEmpty()) {
            item.addUnsafeEnchantments(original.getEnchantments());
            return item;
        }
        return item;
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("keep-enchants", -251);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("keep-enchants") == null;
    }
}

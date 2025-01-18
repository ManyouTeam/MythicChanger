package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class KeepEnchants extends AbstractChangesRule {

    public KeepEnchants() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (singleChange.getBoolean("keep-enchants") && !singleChange.getOriginal().getEnchantments().isEmpty()) {
            singleChange.getItem().addUnsafeEnchantments(singleChange.getOriginal().getEnchantments());
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
        return section.get("keep-enchants") == null;
    }
}

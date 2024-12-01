package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class SetAmount extends AbstractChangesRule {

    public SetAmount() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        singleChange.getItem().setAmount(singleChange.getInt("set-amount"));
        return singleChange.getItem();
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("set-amount", 59);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getString("set-amount") == null;
    }
}

package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetCustomModelData extends AbstractChangesRule {

    public SetCustomModelData() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ItemMeta meta = singleChange.getItemMeta();
        meta.setCustomModelData(singleChange.getInt("set-custom-model-data"));
        return singleChange.setItemMeta(meta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("set-custom-model-data", 5);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getInt("set-custom-model-data", -1) < 0;
    }
}

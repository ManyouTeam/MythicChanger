package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class SetType extends AbstractChangesRule {

    public SetType() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        Material material = Material.getMaterial(singleChange.getString("set-type"));
        if (material != null) {
            singleChange.getItem().setType(material);
        }
        return singleChange.getItem();
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("set-type", 60);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getString("set-type") == null;
    }
}

package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.methods.BuildItem;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.inventory.ItemStack;

public class KeepItemFormat extends AbstractChangesRule {

    public KeepItemFormat() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (singleChange.getBoolean("keep-item-format.replace-item", false)) {
            singleChange.setNeedRewriteItem();
        }
        ConfigurationSection debuildSection = singleChange.getDebuildItemFormat();
        ConfigurationSection keepSection = new MemoryConfiguration();
        for (String key : singleChange.getStringList("keep-item-format")) {
            if (debuildSection.contains(key)) {
                keepSection.set(key, debuildSection.get(key));
            }
        }
        return BuildItem.editItemStack(singleChange.getPlayer(),
                singleChange.getItem(),
                keepSection);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("keep-item-format", -259);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("keep-item-format");
    }
}

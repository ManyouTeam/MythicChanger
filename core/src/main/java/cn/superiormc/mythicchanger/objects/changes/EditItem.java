package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.methods.BuildItem;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class EditItem extends AbstractChangesRule {

    public EditItem() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (singleChange.getBoolean("edit-item.replace-item", false)) {
            singleChange.setNeedRewriteItem();
        }
        return BuildItem.editItemStack(singleChange.getPlayer(),
                singleChange.getItem(),
                singleChange.getConfigurationSection("edit-item"));
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("edit-item", 1);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("edit-item") == null;
    }
}

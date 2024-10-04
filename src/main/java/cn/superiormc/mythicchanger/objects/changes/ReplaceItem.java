package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.methods.BuildItem;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ReplaceItem extends AbstractChangesRule {

    public ReplaceItem() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section,
                               ItemStack original,
                               ItemStack item,
                               Player player,
                               boolean fakeOrReal,
                               boolean isPlayerInventory) {
        ConfigurationSection itemSection = section.getConfigurationSection("replace-item");
        ItemStack result = BuildItem.buildItemStack(itemSection);
        if (result.getType() == Material.BARRIER) {
            return item;
        }
        return result;
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("replace-item", 1000);
    }

    @Override
    public boolean getNeedRewriteItem(ConfigurationSection section) {
        return true;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("replace-item") == null;
    }
}

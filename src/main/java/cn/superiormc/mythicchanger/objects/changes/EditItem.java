package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.methods.BuildItem;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class EditItem extends AbstractChangesRule {

    public EditItem() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section,
                               ItemStack original,
                               ItemStack item,
                               Player player,
                               boolean fakeOrReal,
                               boolean isPlayerInventory) {
        return BuildItem.editItemStack(player,
                item,
                Objects.requireNonNull(section.getConfigurationSection("edit-item")), "name", ItemUtil.getItemName(item));
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("edit-item", 1);
    }

    @Override
    public boolean getNeedRewriteItem(ConfigurationSection section) {
        return section.getBoolean("edit-item.replace-item", false);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("edit-item") == null;
    }
}

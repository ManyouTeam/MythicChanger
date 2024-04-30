package cn.superiormc.mythicchanger.objects.changes;

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
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player, boolean fakeOrReal) {
        return BuildItem.editItemStack(item, Objects.requireNonNull(section.getConfigurationSection("edit-item")), "name", ItemUtil.getItemName(item));
    }

    @Override
    public int getWeight() {
        return 1;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("edit-item") == null;
    }
}

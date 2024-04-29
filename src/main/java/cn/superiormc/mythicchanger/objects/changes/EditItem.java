package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.methods.BuildItem;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EditItem extends AbstractChangesRule {

    public EditItem() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player, boolean fakeOrReal) {
        ConfigurationSection tempVal1 = section.getConfigurationSection("edit-item");
        if (tempVal1 == null) {
            return item;
        }
        return BuildItem.editItemStack(item, tempVal1, "name", ItemUtil.getItemName(item));
    }

    @Override
    public int getWeight() {
        return 1;
    }
}

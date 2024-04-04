package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.utils.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ReplaceItem extends AbstractChangesRule {

    public ReplaceItem() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player, boolean fakeOrReal) {
        ConfigurationSection tempVal1 = section.getConfigurationSection("replace-item");
        if (tempVal1 == null) {
            return item;
        }
        return ItemUtil.buildItemStack(tempVal1);
    }

    @Override
    public int getWeight() {
        return 1000;
    }

    @Override
    public boolean getNeedRewriteItem() {
        return true;
    }
}

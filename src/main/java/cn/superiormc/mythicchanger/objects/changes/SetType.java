package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.ErrorManager;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetType extends AbstractChangesRule {

    public SetType() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player, boolean fakeOrReal) {
        if (section.getString("set-type") == null) {
            return item;
        }
        Material material = Material.getMaterial(section.getString("set-type"));
        if (material == null) {
            return item;
        }
        item.setType(material);
        return item;
    }

    @Override
    public int getWeight() {
        return 60;
    }
}

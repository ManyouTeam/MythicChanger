package cn.superiormc.mythicchanger.objects.changes;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class SetType extends AbstractChangesRule {

    public SetType() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player, boolean fakeOrReal) {
        Material material = Material.getMaterial(Objects.requireNonNull(section.getString("set-type")));
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

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getString("set-type") == null;
    }
}

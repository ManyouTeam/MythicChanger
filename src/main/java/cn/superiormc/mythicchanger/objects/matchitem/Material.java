package cn.superiormc.mythicchanger.objects.matchitem;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Material extends AbstractMatchItemRule {
    public Material() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        List<String> materials = new ArrayList<>();
        for (String singleMaterial : section.getStringList("material")) {
            materials.add(singleMaterial.toLowerCase());
        }
        return materials.contains(item.getType().getKey().getKey());
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("material").isEmpty();
    }
}

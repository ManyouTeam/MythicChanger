package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.methods.DebuildItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemFormat extends AbstractMatchItemRule{
    public ItemFormat() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        ConfigurationSection itemSection = DebuildItem.debuildItem(item, new MemoryConfiguration());
        ConfigurationSection requireSection = section.getConfigurationSection("item-format");
        for (String key : requireSection.getKeys(true)) {
            if (itemSection.contains(key) && !itemSection.get(key).equals(requireSection.get(key))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("item-format") == null;
    }
}

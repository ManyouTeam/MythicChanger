package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetCustomModelData extends AbstractChangesRule {

    public SetCustomModelData() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack original, ItemStack item, Player player, boolean fakeOrReal) {
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(section.getInt("set-custom-model-data"));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("set-custom-model-data", 5);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getInt("set-custom-model-data", -1) < 0;
    }
}

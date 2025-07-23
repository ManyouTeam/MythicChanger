package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.ErrorManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetLore extends AbstractChangesRule {

    public SetLore() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (singleChange.isFakeOrReal() && !ConfigManager.configManager.getBoolean("ignore-warning")) {
            ErrorManager.errorManager.sendErrorMessage("§6Warning: set-lore rule has incompatibility issues with" +
                    " other packet based item plugins, it is recommend that remove it in fake changes from all your rule configs!" +
                    " If you want to ignore this warning, please disable warning display in config.yml file.");
        }
        ItemMeta meta = singleChange.getItemMeta();
        meta.setLore(singleChange.getStringList("set-lore"));
        return singleChange.setItemMeta(meta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("set-lore", 6);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("set-lore").isEmpty();
    }
}

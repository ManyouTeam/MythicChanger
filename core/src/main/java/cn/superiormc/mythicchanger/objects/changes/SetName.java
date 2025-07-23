package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.ErrorManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetName extends AbstractChangesRule {

    public SetName() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (singleChange.isFakeOrReal() && !ConfigManager.configManager.getBoolean("ignore-warning")) {
            ErrorManager.errorManager.sendErrorMessage("§6Warning: set-name rule has incompatibility issues with" +
                    " other packet based item plugins, it is recommend that remove it in fake changes from all your rule configs!" +
                    " If you want to ignore this warning, please disable warning display in config.yml file.");
        }
        ItemMeta meta = singleChange.getItemMeta();
        MythicChanger.methodUtil.setItemName(meta, singleChange.getString("set-name"), singleChange.getPlayer());
        return singleChange.setItemMeta(meta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("set-name", 7);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getString("set-name") == null;
    }
}

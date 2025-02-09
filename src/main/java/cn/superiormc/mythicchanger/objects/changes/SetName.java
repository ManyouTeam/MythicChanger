package cn.superiormc.mythicchanger.objects.changes;

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
            ErrorManager.errorManager.sendErrorMessage("§x§9§8§F§B§9§8[MythicChanger] §6Warning: set-name rule has incompatibility issues with" +
                    " other packet based item plugins, it is recommend that remove it in fake changes from all your rule configs!" +
                    " If you want to ignore this warning, please disable warning display in config.yml file.");
        }
        ItemMeta meta = singleChange.getItemMeta();
        meta.setDisplayName(CommonUtil.modifyString(singleChange.getString("set-name"), "name", ItemUtil.getItemName(singleChange.getItem())));
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

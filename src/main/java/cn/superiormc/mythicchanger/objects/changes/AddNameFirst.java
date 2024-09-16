package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.ErrorManager;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AddNameFirst extends AbstractChangesRule {

    public AddNameFirst() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack original, ItemStack item, Player player, boolean fakeOrReal) {
        if (!fakeOrReal && !ConfigManager.configManager.getBoolean("bypass-real-change-limit")) {
            ErrorManager.errorManager.sendErrorMessage("§x§9§8§F§B§9§8[MythicChanger] §cError: add-name-first rule only supports" +
                    " fake change, please remove it in real changes from all your rule configs! If you want to bypass this limit, " +
                    "please disable limit check in config.yml file.");
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        String tempVal1 = TextUtil.parse(section.getString("add-name-first"), player) + ItemUtil.getItemName(item);
        meta.setDisplayName(tempVal1);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("add-name-first", 21);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getString("add-name-first") == null;
    }
}

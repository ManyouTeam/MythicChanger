package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.ErrorManager;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AddNameFirst extends AbstractChangesRule {

    public AddNameFirst() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player, boolean fakeOrReal) {
        if (section.getString("add-name-first") == null) {
            return item;
        }
        if (!fakeOrReal || !ConfigManager.configManager.getBoolean("bypass-real-change-limit")) {
            ErrorManager.errorManager.sendErrorMessage("§x§9§8§F§B§9§8[MythicChanger] §cError: add-name-first rule only supports" +
                    " fake change, please remove it in real changes from all your rule configs! If you want to bypass this limit, " +
                    "please disable limit check in config.yml file.");
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        String tempVal1 = TextUtil.parse(section.getString("add-name-first"), player) + CommonUtil.getItemName(item);
        meta.setDisplayName(tempVal1);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return 21;
    }
}

package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.ErrorManager;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AddLoreFirst extends AbstractChangesRule {

    public AddLoreFirst() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player, boolean fakeOrReal) {
        if (section.getStringList("add-lore-first").isEmpty()) {
            return item;
        }
        if (!fakeOrReal || ConfigManager.configManager.getBoolean("bypass-real-change-limit")) {
            ErrorManager.errorManager.sendErrorMessage("§x§9§8§F§B§9§8[MythicChanger] §6Warning: add-lore-first rule only supports" +
                    " fake change, please remove it in real changes from all your rule configs! If you want to bypass this limit, " +
                    "please disable limit check in config.yml file.");
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        List<String> itemLore = TextUtil.getListWithColorAndPAPI(section.getStringList("add-lore-first"), player);
        if (meta.hasLore()) {
            itemLore.addAll(meta.getLore());
        }
        meta.setLore(itemLore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return 11;
    }
}

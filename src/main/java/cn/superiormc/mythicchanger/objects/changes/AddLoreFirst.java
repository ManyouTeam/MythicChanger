package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.ErrorManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
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
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (!singleChange.isFakeOrReal() && !ConfigManager.configManager.getBoolean("bypass-real-change-limit")) {
            ErrorManager.errorManager.sendErrorMessage("§cError: add-lore-first rule only supports" +
                    " fake change, please remove it in real changes from all your rule configs! If you want to bypass this limit, " +
                    "please disable limit check in config.yml file.");
            return singleChange.getItem();
        }
        ItemMeta meta = singleChange.getItemMeta();
        List<String> itemLore = singleChange.getStringList("add-lore-first");
        if (meta.hasLore()) {
            itemLore.addAll(meta.getLore());
        }
        meta.setLore(itemLore);
        return singleChange.setItemMeta(meta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("add-lore-first", 11);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("add-lore-first").isEmpty();
    }
}

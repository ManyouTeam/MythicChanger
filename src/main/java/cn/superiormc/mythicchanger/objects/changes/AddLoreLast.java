package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.ErrorManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AddLoreLast extends AbstractChangesRule {

    public AddLoreLast() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (!singleChange.isFakeOrReal() && !ConfigManager.configManager.getBoolean("bypass-real-change-limit")) {
            ErrorManager.errorManager.sendErrorMessage("§cError: add-lore-last rule only supports" +
                    " fake change, please remove it in real changes from all your rule configs! If you want to bypass this limit, " +
                    "please disable limit check in config.yml file.");
            return singleChange.getItem();
        }
        ItemMeta meta = singleChange.getItemMeta();
        List<String> itemLore = meta.getLore();
        if (itemLore == null) {
            itemLore = new ArrayList<>();
        }
        itemLore.addAll(singleChange.getStringList("add-lore-last"));
        meta.setLore(itemLore);
        return singleChange.setItemMeta(meta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("add-lore-last", 12);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("add-lore-last").isEmpty();
    }
}

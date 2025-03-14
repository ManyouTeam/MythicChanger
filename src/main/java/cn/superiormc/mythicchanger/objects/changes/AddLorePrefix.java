package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AddLorePrefix extends AbstractChangesRule {

    public AddLorePrefix() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ItemMeta meta = singleChange.getItemMeta();
        if (!meta.hasLore()) {
            return singleChange.getItem();
        }
        List<String> itemLore = meta.getLore();
        List<String> newLore = new ArrayList<>();
        for (String lore : itemLore) {
            if (lore.startsWith("§w")) {
                newLore.add(TextUtil.parse(lore, singleChange.getPlayer()));
                continue;
            }
            newLore.add("§w" + singleChange.getString("add-lore-prefix") + lore);
        }
        meta.setLore(newLore);
        return singleChange.setItemMeta(meta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("add-lore-prefix", 13);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getString("add-lore-prefix") == null;
    }
}

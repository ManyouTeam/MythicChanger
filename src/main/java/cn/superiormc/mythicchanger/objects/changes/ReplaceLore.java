package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ReplaceLore extends AbstractChangesRule {

    public ReplaceLore() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ConfigurationSection tempVal1 = singleChange.getConfigurationSection("replace-lore");
        ItemMeta meta = singleChange.getItemMeta();
        if (!meta.hasLore()) {
            return singleChange.getItem();
        }
        List<String> newLore = new ArrayList<>();
        for (String hasLore : singleChange.getItemMeta().getLore()) {
            for (String requiredLore : tempVal1.getKeys(false)) {
                if (hasLore.contains(requiredLore)) {
                    hasLore = hasLore.replace(requiredLore, TextUtil.parse(tempVal1.getString(requiredLore), singleChange.getPlayer()));
                }
            }
            String[] tempVal2 = hasLore.split("\\\\n");
            if (tempVal2.length > 1) {
                for (String string : tempVal2) {
                    if (string.isEmpty()) {
                        continue;
                    }
                    newLore.add(TextUtil.parse(string));
                }
                continue;
            }
            newLore.add(hasLore);
        }
        meta.setLore(newLore);
        return singleChange.setItemMeta(meta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("replace-lore", 9);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("replace-lore") == null;
    }
}

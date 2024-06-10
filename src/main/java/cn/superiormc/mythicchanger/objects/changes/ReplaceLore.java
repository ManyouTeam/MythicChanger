package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ReplaceLore extends AbstractChangesRule {

    public ReplaceLore() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack original, ItemStack item, Player player, boolean fakeOrReal) {
        ConfigurationSection tempVal1 = section.getConfigurationSection("replace-lore");
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            return item;
        }
        List<String> newLore = new ArrayList<>();
        for (String hasLore : item.getItemMeta().getLore()) {
            for (String requiredLore : tempVal1.getKeys(false)) {
                if (hasLore.contains(requiredLore)) {
                    hasLore = hasLore.replace(requiredLore, TextUtil.parse(tempVal1.getString(requiredLore), player));
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
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getWeight() {
        return 9;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("replace-lore") == null;
    }
}

package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class EditLore extends AbstractChangesRule {

    public EditLore() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        try {
            ItemMeta meta = singleChange.getItemMeta();
            if (!meta.hasLore()) {
                return singleChange.getItem();
            }
            ConfigurationSection editLoreSection = singleChange.section.getConfigurationSection("edit-lore");
            if (editLoreSection == null) {
                return singleChange.getItem();
            }
            List<String> itemLore = meta.getLore();
            for (String key : editLoreSection.getKeys(false)) {
                String parsedKey = key.replace("last", String.valueOf(itemLore.size()));
                int line = Integer.parseInt(parsedKey);
                if (line > itemLore.size()) {
                    if (singleChange.section.getBoolean("edit-lore-bypass", false)) {
                        continue;
                    } else {
                        itemLore.add(TextUtil.parse(CommonUtil.modifyString(editLoreSection.getString(key), "original", "")));
                    }
                } else {
                    itemLore.set(line - 1, TextUtil.parse(CommonUtil.modifyString(editLoreSection.getString(key), "original", itemLore.get(line - 1))));
                }
                return singleChange.setItemMeta(meta);
            }
            return singleChange.getItem();
        } catch (Throwable throwable) {
            return singleChange.getItem();
        }
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("edit-lore", 14);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("edit-lore");
    }
}

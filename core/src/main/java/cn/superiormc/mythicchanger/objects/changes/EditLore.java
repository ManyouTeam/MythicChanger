package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Locale;

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
            ObjectSingleChange editLoreSection = singleChange.getConfigurationSection("edit-lore");
            if (editLoreSection == null) {
                return singleChange.getItem();
            }
            String mode = singleChange.getString("edit-lore-mode", "REPLACE").toUpperCase(Locale.ROOT);
            if (!mode.equals("ADD") && !mode.equals("REMOVE") && !mode.equals("REPLACE")) {
                return singleChange.getItem();
            }
            List<String> itemLore = MythicChanger.methodUtil.getItemLore(meta);
            for (String key : editLoreSection.getKeys(false)) {
                String parsedKey = key.replace("last", String.valueOf(itemLore.size()));
                int line = Integer.parseInt(parsedKey);
                if (line > itemLore.size()) {
                    if (singleChange.getBoolean("edit-lore-bypass", false)) {
                        continue;
                    } else if (!mode.equals("REMOVE")) {
                        itemLore.add(CommonUtil.modifyString(singleChange.getPlayer(), editLoreSection.getString(key), "original", ""));
                    }
                } else {
                    int targetLine = line - 1;
                    String original = itemLore.get(targetLine);
                    switch (mode) {
                        case "ADD":
                            itemLore.add(targetLine + 1, CommonUtil.modifyString(singleChange.getPlayer(),
                                    editLoreSection.getString(key), "original", original));
                            break;
                        case "REMOVE":
                            itemLore.remove(targetLine);
                            break;
                        case "REPLACE":
                            itemLore.set(targetLine, CommonUtil.modifyString(singleChange.getPlayer(),
                                    editLoreSection.getString(key), "original", original));
                            break;
                    }
                }
                if (itemLore.isEmpty()) {
                    meta.setLore(null);
                } else {
                    MythicChanger.methodUtil.setItemLore(meta, itemLore, singleChange.getPlayer());
                }
                return singleChange.setItemMeta(meta);
            }
            return singleChange.getItem();
        } catch (Throwable throwable) {
            return singleChange.getItem();
        }
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("edit-lore");
    }
}

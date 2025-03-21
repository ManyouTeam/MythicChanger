package cn.superiormc.mythicchanger.utils;

import cn.superiormc.mythicchanger.manager.LocateManager;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {

    public static String getItemName(ItemStack displayItem) {
        if (displayItem == null || displayItem.getItemMeta() == null) {
            return "ERROR: Unknown Item";
        }
        if (CommonUtil.getMinorVersion(20, 5)) {
            if (displayItem.getItemMeta().hasItemName()) {
                return displayItem.getItemMeta().getItemName();
            }
        }
        if (displayItem.getItemMeta().hasDisplayName()) {
            return displayItem.getItemMeta().getDisplayName();
        }
        if (LocateManager.enableThis() && LocateManager.locateManager != null) {
            return LocateManager.locateManager.getLocateName(displayItem);
        }
        return getItemNameWithoutVanilla(displayItem);
    }

    public static String getItemNameWithoutVanilla(ItemStack displayItem) {
        if (displayItem == null || displayItem.getItemMeta() == null) {
            return "ERROR: Unknown Item";
        }
        if (displayItem.getItemMeta().hasDisplayName()) {
            return displayItem.getItemMeta().getDisplayName();
        }
        StringBuilder result = new StringBuilder();
        for (String word : displayItem.getType().name().toLowerCase().split("_")) {
            if (!word.isEmpty()) {
                char firstChar = Character.toUpperCase(word.charAt(0));
                String restOfWord = word.substring(1);
                result.append(firstChar).append(restOfWord).append(" ");
            }
        }
        return result.toString();
    }

    public static boolean isValid(ItemStack item) {
        return item != null && !item.getType().isAir();
    }
}

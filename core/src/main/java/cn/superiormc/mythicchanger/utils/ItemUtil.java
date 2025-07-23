package cn.superiormc.mythicchanger.utils;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.LocateManager;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {

    // No Color Code
    public static String getItemName(ItemStack displayItem) {
        if (displayItem == null || displayItem.getItemMeta() == null) {
            return "";
        }
        if (CommonUtil.getMinorVersion(20, 5)) {
            if (displayItem.getItemMeta().hasItemName()) {
                return MythicChanger.methodUtil.getItemItemName(displayItem.getItemMeta());
            }
        }
        if (displayItem.getItemMeta().hasDisplayName()) {
            return MythicChanger.methodUtil.getItemName(displayItem.getItemMeta());
        }
        if (LocateManager.enableThis() && LocateManager.locateManager != null) {
            return LocateManager.locateManager.getLocateName(displayItem);
        }
        return getItemNameWithoutVanilla(displayItem);
    }

    // No Color Code
    public static String getItemNameWithoutVanilla(ItemStack displayItem) {
        if (displayItem == null || displayItem.getItemMeta() == null) {
            return "";
        }
        if (displayItem.getItemMeta().hasDisplayName()) {
            return MythicChanger.methodUtil.getItemName(displayItem.getItemMeta());
        }
        if (MythicChanger.methodUtil.methodID().equals("paper")) {
            return "<lang:" + displayItem.translationKey() + ">";
        }
        StringBuilder result = new StringBuilder();
        for (String word : displayItem.getType().name().toLowerCase().split("_")) {
            if (!word.isEmpty()) {
                char firstChar = Character.toUpperCase(word.charAt(0));
                String restOfWord = word.substring(1);
                result.append(firstChar).append(restOfWord).append(" ");
            }
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    public static boolean isValid(ItemStack item) {
        return item != null && !item.getType().isAir();
    }
}

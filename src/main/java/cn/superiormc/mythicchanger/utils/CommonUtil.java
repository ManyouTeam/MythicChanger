package cn.superiormc.mythicchanger.utils;

import cn.superiormc.mythicchanger.MythicChanger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import pers.neige.neigeitems.utils.ItemUtils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    public static boolean checkPluginLoad(String pluginName){
        return MythicChanger.instance.getServer().getPluginManager().isPluginEnabled(pluginName);
    }

    public static boolean getClass(String className) {
        try {
            Class.forName(className);
            return true;
        }
        catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static int getMajorVersion() {
        String version = Bukkit.getVersion();
        Matcher matcher = Pattern.compile("MC: \\d\\.(\\d+)").matcher(version);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 20;
    }

    public static boolean inPlayerInventory(Player player, int slot) {
        int topSize = player.getOpenInventory().getTopInventory().getSize();
        if (player.getOpenInventory().getTopInventory() instanceof CraftingInventory &&
                player.getOpenInventory().getTopInventory().getSize() == 5) {
            return slot >= 5 && slot <= 44;
        }
        return slot >= topSize;
    }

    public static String getItemName(ItemStack displayItem) {
        if (displayItem == null || displayItem.getItemMeta() == null) {
            return "";
        }
        if (CommonUtil.checkPluginLoad("NeigeItems")) {
            return ItemUtils.getItemName(displayItem);
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

    public static void mkDir(File dir) {
        if (!dir.exists()) {
            File parentFile = dir.getParentFile();
            if (parentFile == null) {
                return;
            }
            String parentPath = parentFile.getPath();
            mkDir(new File(parentPath));
            dir.mkdir();
        }
    }

    // Unused but save here for anyone maybe need it.
    public static int convertNMSSlotToBukkitSlot(int slot, int windowID, Player player) {
        if (windowID == 0) {
            if (slot < 5 || slot > 44) {
                return -1;
            }
            int spigotSlot;
            if (slot >= 36) {
                spigotSlot = slot - 36;
            } else if (slot <= 8) {
                spigotSlot = slot + 31;
            } else {
                spigotSlot = slot;
            }
            return spigotSlot;
        } else {
            int topSize = player.getOpenInventory().getTopInventory().getSize();
            if (topSize == 5 && CommonUtil.inPlayerInventory(player, slot)) {
                topSize = 9;
            }
            if (slot < topSize || slot > topSize + 36) {
                return -1;
            }
            int spigotSlot;
            // 如果是最后9个格子
            if (slot >= 27 + topSize) {
                spigotSlot = slot - 27 - topSize;
                // 如果是中间三排
            } else {
                spigotSlot = slot - topSize + 9;
            }
            return spigotSlot;
        }
    }
}

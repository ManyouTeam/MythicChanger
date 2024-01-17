package cn.superiormc.mythicchanger.utils;

import cn.superiormc.mythicchanger.MythicChanger;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import pers.neige.neigeitems.utils.ItemUtils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    public static boolean checkPluginLoad(String pluginName){
        return MythicChanger.instance.getServer().getPluginManager().isPluginEnabled(pluginName);
    }

    public static int getMajorVersion() {
        String version = Bukkit.getVersion();
        Matcher matcher = Pattern.compile("MC: \\d\\.(\\d+)").matcher(version);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 20;
    }

    public static String getItemName(ItemStack displayItem) {
        if (displayItem == null) {
            return "";
        }
        if (CommonUtil.checkPluginLoad("NeigeItems")) {
            return ItemUtils.getItemName(displayItem);
        }
        if (displayItem.getItemMeta() == null) {
            return displayItem.getType().name().replace("_", " ");
        }
        return displayItem.getItemMeta().hasDisplayName() ?
                displayItem.getItemMeta().getDisplayName() :
                displayItem.getItemMeta().hasLocalizedName() ?
                        displayItem.getItemMeta().getLocalizedName() :
                        displayItem.getType().name().replace("_", " ");
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
}

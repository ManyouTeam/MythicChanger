package cn.superiormc.mythicchanger.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TextUtil {

    public static String parse(String text) {
        return ColorParser.parse(text);
    }

    public static String parse(String text, Player player) {
        if (CommonUtil.checkPluginLoad("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player, parse(text));
        }
        else {
            return parse(text);
        }
    }

    public static String withPAPI(String text, Player player) {
        if (text.contains("%") && CommonUtil.checkPluginLoad("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player, text);
        }
        else {
            return text;
        }
    }

    public static List<String> getListWithColorAndPAPI(List<String> inList, Player player) {
        List<String> resultList = new ArrayList<>();
        for (String s : inList) {
            if (CommonUtil.checkPluginLoad("PlaceholderAPI")) {
                s = PlaceholderAPI.setPlaceholders(player, s);
            }
            resultList.add(TextUtil.parse(s));
        }
        return resultList;
    }

    public static List<String> getListWithPAPI(List<String> inList, Player player) {
        List<String> resultList = new ArrayList<>();
        for (String s : inList) {
            if (CommonUtil.checkPluginLoad("PlaceholderAPI")) {
                s = PlaceholderAPI.setPlaceholders(player, s);
            }
            resultList.add(s);
        }
        return resultList;
    }
}

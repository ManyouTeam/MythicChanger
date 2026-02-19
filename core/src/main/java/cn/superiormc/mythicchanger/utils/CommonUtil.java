package cn.superiormc.mythicchanger.utils;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.LanguageManager;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    public static Map<String, Boolean> loadedPlugins = new HashMap<>();

    public static boolean checkPluginLoad(String pluginName) {
        if (loadedPlugins.containsKey(pluginName)) {
            return loadedPlugins.get(pluginName);
        }
        loadedPlugins.put(pluginName, MythicChanger.instance.getServer().getPluginManager().isPluginEnabled(pluginName));
        return MythicChanger.instance.getServer().getPluginManager().isPluginEnabled(pluginName);
    }

    public static boolean getClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean checkClass(String className, String methodName) {
        try {
            Class<?> targetClass = Class.forName(className);
            Method[] methods = targetClass.getDeclaredMethods();

            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    return true;
                }
            }

            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean getYearVersion(int year, int majorVersion, int minorVersion) {
        return MythicChanger.yearVersion > year || (MythicChanger.yearVersion == year && MythicChanger.majorVersion >= majorVersion && MythicChanger.minorVersion >= minorVersion);
    }

    public static boolean getMajorVersion(int version) {
        return MythicChanger.yearVersion > 1 || MythicChanger.majorVersion >= version;
    }

    public static boolean getMinorVersion(int majorVersion, int minorVersion) {
        return MythicChanger.yearVersion > 1 || MythicChanger.majorVersion > majorVersion || (MythicChanger.majorVersion == majorVersion &&
                MythicChanger.minorVersion >= minorVersion);
    }

    public static void summonMythicMobs(Location location, String mobID, int level) {
        MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob(mobID).orElse(null);
        if (mob != null) {
            mob.spawn(BukkitAdapter.adapt(location), level);
        }
    }

    public static String modifyString(Player player, String text, String... args) {
        text = CommonUtil.parseLang(player, text);
        for (int i = 0 ; i < args.length ; i += 2) {
            String var1 = "{" + args[i] + "}";
            String var2 = "%" + args[i] + "%";
            if (args[i + 1] == null) {
                text = text.replace(var1, "").replace(var2, "");
            } else {
                text = text.replace(var1, args[i + 1]).replace(var2, args[i + 1]);
            }
        }
        return text;
    }

    public static List<String> modifyList(Player player, List<String> config, String... args) {
        List<String> resultList = new ArrayList<>();
        for (String s : config) {
            s = CommonUtil.parseLang(player, s);
            for (int i = 0 ; i < args.length ; i += 2) {
                String var = "{" + args[i] + "}";
                if (args[i + 1] == null) {
                    s = s.replace(var, "");
                } else {
                    s = s.replace(var, args[i + 1]);
                }
            }
            String[] tempVal1 = s.split(";;");
            if (tempVal1.length > 1) {
                for (String string : tempVal1) {
                    resultList.add(TextUtil.withPAPI(string, player));
                }
                continue;
            }
            resultList.add(TextUtil.withPAPI(s, player));
        }
        return resultList;
    }

    public static String parseLang(Player player, String text) {
        Pattern pattern8 = Pattern.compile("\\{lang:(.*?)}");
        Matcher matcher8 = pattern8.matcher(text);
        while (matcher8.find()) {
            String placeholder = matcher8.group(1);
            text = text.replace("{lang:" + placeholder + "}", LanguageManager.languageManager.getStringText(player, "override-lang." + placeholder));
        }
        return text;
    }

    public static boolean inPlayerInventory(Player player, int slot, int windowID) {
        int topSize = player.getOpenInventory().getTopInventory().getSize();
        if (windowID == 0 || (player.getOpenInventory().getTopInventory() instanceof CraftingInventory &&
                player.getOpenInventory().getTopInventory().getSize() == 5)) {
            return slot >= 5 && slot <= 44;
        }
        return slot >= topSize;
    }

    public static ItemStack getItemFromSlot(Player player, int slot) {
        if (slot == 36) {
            return player.getInventory().getItem(EquipmentSlot.HEAD);
        } else if (slot == 37) {
            return player.getInventory().getItem(EquipmentSlot.CHEST);
        } else if (slot == 38) {
            return player.getInventory().getItem(EquipmentSlot.LEGS);
        } else if (slot == 39) {
            return player.getInventory().getItem(EquipmentSlot.FEET);
        } else if (slot == 40) {
            return player.getInventory().getItem(EquipmentSlot.OFF_HAND);
        } else if (slot == -10000) {
            return new ItemStack(Material.AIR);
        }
        return player.getInventory().getItem(slot);
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
                return -10000;
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
            if (topSize == 5 && CommonUtil.inPlayerInventory(player, slot, windowID)) {
                topSize = 9;
            }
            if (slot < topSize || slot > topSize + 36) {
                return -10000;
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

    public static NamespacedKey parseNamespacedKey(String key) {
        String[] keySplit = key.split(":");
        if (keySplit.length == 1) {
            return NamespacedKey.minecraft(key.toLowerCase());
        }
        if (CommonUtil.getMajorVersion(16)) {
            return NamespacedKey.fromString(key);
        }
        return new NamespacedKey("mythicchanger", "unknown");
    }

    public static Color parseColor(String color) {
        if (color == null || color.isEmpty()) {
            return Color.fromRGB(0, 0, 0);
        }

        color = color.trim();

        // 支持 #RRGGBB
        if (color.startsWith("#")) {
            return Color.fromRGB(Integer.parseInt(color.substring(1), 16));
        }

        // 支持 R,G,B
        String[] keySplit = color.replace(" ", "").split(",");
        if (keySplit.length == 3) {
            return Color.fromRGB(
                    Integer.parseInt(keySplit[0]),
                    Integer.parseInt(keySplit[1]),
                    Integer.parseInt(keySplit[2])
            );
        }

        // 默认：单值 RGB int
        return Color.fromRGB(Integer.parseInt(color));
    }

    public static String colorToString(Color color) {
        if (color == null) {
            return "0,0,0";
        }
        return color.getRed() + "," + color.getGreen() + "," + color.getBlue();
    }

    public static List<Color> parseColorList(List<String> rawList) {
        List<Color> colors = new ArrayList<>();

        for (String value : rawList) {
            try {
                colors.add(parseColor(value));
            } catch (Exception e) {
                return colors;
            }
        }

        return colors;
    }

    public static JSONObject fetchJson(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return new JSONObject(response.toString());
        }
    }

    public static void giveOrDrop(Player player, ItemStack... item) {
        HashMap<Integer, ItemStack> result = player.getInventory().addItem(item);
        if (!result.isEmpty()) {
            for (int id : result.keySet()) {
                player.getWorld().dropItem(player.getLocation(), result.get(id));
            }
        }
    }
}

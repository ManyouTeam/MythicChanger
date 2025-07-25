package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.methods.BuildItem;
import cn.superiormc.mythicchanger.methods.DebuildItem;
import cn.superiormc.mythicchanger.utils.SchedulerUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private final Map<String, ItemStack> savedItemMap = new HashMap<>();

    private final Map<String, ConfigurationSection> savedItemFormatMap = new HashMap<>();

    public static ItemManager itemManager;

    public ItemManager() {
        itemManager = this;
        initSavedItems();
    }

    public void initSavedItems() {
        savedItemMap.clear();
        File dir = new File(MythicChanger.instance.getDataFolder() + "/items");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File[] tempList = dir.listFiles();
        if (tempList == null) {
            return;
        }
        for (File file : tempList) {
            if (file.getName().endsWith(".yml")) {
                YamlConfiguration section = YamlConfiguration.loadConfiguration(file);
                String key = file.getName();
                key = key.substring(0, key.length() - 4);
                Object object = section.get("item");
                try {
                    if (section.getKeys(false).size() == 1 && object != null) {
                        savedItemMap.put(key, MythicChanger.methodUtil.getItemObject(object));
                        MythicChanger.methodUtil.sendMessage(null, TextUtil.pluginPrefix() + " §fLoaded Bukkit Saved Item: " + key + ".yml!");
                    } else {
                        savedItemFormatMap.put(key, section);
                        MythicChanger.methodUtil.sendMessage(null, TextUtil.pluginPrefix() + " §fLoaded ItemFormat Saved Item: " + key + ".yml!");
                    }
                } catch (Throwable throwable) {
                    ErrorManager.errorManager.sendErrorMessage("§cError: Failed to load Saved Item: " + key + ". If this error always happens, try delete this save item because this save item file maybe break or not support new server or plugin version.");
                }
            }
        }
    }

    public void saveMainHandItem(Player player, String key) {
        ItemStack item = player.getInventory().getItemInMainHand();
        File dir = new File(MythicChanger.instance.getDataFolder() + "/items");
        if (!dir.exists()) {
            dir.mkdir();
        }
        YamlConfiguration briefcase = new YamlConfiguration();
        briefcase.set("item", MythicChanger.methodUtil.makeItemToObject(item));
        String yaml = briefcase.saveToString();
        SchedulerUtil.runTaskAsynchronously(() -> {
            Path path = new File(dir.getPath(), key + ".yml").toPath();
            try {
                Files.write(path, yaml.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        savedItemMap.put(key, item);
    }

    public void saveMainHandItemFormat(Player player, String key) {
        File dir = new File(MythicChanger.instance.getDataFolder() + "/items");
        if (!dir.exists()) {
            dir.mkdir();
        }
        YamlConfiguration briefcase = new YamlConfiguration();
        DebuildItem.debuildItem(player.getInventory().getItemInMainHand(), briefcase);
        String yaml = briefcase.saveToString();
        SchedulerUtil.runTaskAsynchronously(() -> {
            Path path = new File(dir.getPath(), key + ".yml").toPath();
            try {
                Files.write(path, yaml.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        savedItemFormatMap.put(key, briefcase);
    }

    public ItemStack getItemByKey(Player player, String key) {
        if (savedItemMap.containsKey(key)) {
            return savedItemMap.get(key).clone();
        }
        if (savedItemFormatMap.containsKey(key) && player != null) {
            return BuildItem.buildItemStack(player, savedItemFormatMap.get(key));
        }
        return null;
    }

    public Map<String, ItemStack> getSavedItemMap() {
        return savedItemMap;
    }

    public Map<String, ConfigurationSection> getSavedItemFormatMap() {
        return savedItemFormatMap;
    }
}

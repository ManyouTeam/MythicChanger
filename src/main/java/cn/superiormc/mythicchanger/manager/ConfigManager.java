package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.objects.ObjectApplyItem;
import cn.superiormc.mythicchanger.objects.ObjectSingleRule;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

public class ConfigManager {

    public static ConfigManager configManager;

    public FileConfiguration config;

    public Map<String, ObjectSingleRule> ruleMap = new TreeMap<>();

    public Map<String, ObjectApplyItem> itemMap = new HashMap<>();

    public Collection<ObjectSingleRule> ruleCaches = new TreeSet<>();

    public ConfigManager() {
        configManager = this;
        config = MythicChanger.instance.getConfig();
        initRulesConfigs();
        if (!MythicChanger.freeVersion) {
            initApplyItemConfigs();
        }
    }

    private void initRulesConfigs() {
        File dir = new File(MythicChanger.instance.getDataFolder(), "rules");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            String fileName = file.getName();
            if (fileName.endsWith(".yml")) {
                String substring = fileName.substring(0, fileName.length() - 4);
                ObjectSingleRule rule = new ObjectSingleRule(substring, YamlConfiguration.loadConfiguration(file));
                ruleCaches.add(rule);
                ruleMap.put(substring, rule);
                Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fLoaded change rule: " +
                        fileName + "!");
            }
        }
    }

    private void initApplyItemConfigs() {
        MythicChanger.instance.saveDefaultConfig();
        ConfigurationSection tempVal1 = MythicChanger.instance.getConfig().getConfigurationSection(
                "apply-items");
        if (tempVal1 == null) {
            return;
        }
        itemMap = new HashMap<>();
        for (String key : tempVal1.getKeys(false)) {
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fLoaded apply item: " + key + "!");
            itemMap.put(key, new ObjectApplyItem(key, tempVal1.getConfigurationSection(key)));
        }
    }

    public ItemStack startFakeChange(ItemStack item, Player player) {
        if (item == null || item.getType().isAir()) {
            return item;
        }
        for (ObjectSingleRule rule: ruleCaches) {
            if (rule.getMatchItem(item, true)) {
                item = rule.setFakeChange(item, player);
            }
        }
        return item;
    }

    public ItemStack startRealChange(ItemStack item, Player player) {
        if (item == null || item.getType().isAir()) {
            return null;
        }
        boolean needReturnNewItem = false;
        for (ObjectSingleRule rule: ruleCaches) {
            if (rule.getMatchItem(item, false)) {
                ItemStack tempVal1 = rule.setRealChange(item, player);
                if (tempVal1 != null) {
                    item = tempVal1;
                    needReturnNewItem = true;
                }
            }
        }
        if (needReturnNewItem) {
            return item;
        }
        return null;
    }

    public ObjectSingleRule getRule(String id) {
        if (id == null) {
            return null;
        }
        return ruleMap.get(id);
    }

    public ObjectApplyItem getApplyItem(String id) {
        if (id == null) {
            return null;
        }
        return itemMap.get(id);
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(path, true);
    }

}

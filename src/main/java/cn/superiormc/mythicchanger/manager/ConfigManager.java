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
        loadRules(dir);
    }

    private void loadRules(File folder) {
        File[] files = folder.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                loadRules(file); // 递归调用以加载子文件夹内的文件
            } else {
                String fileName = file.getName();
                if (fileName.endsWith(".yml")) {
                    String substring = fileName.substring(0, fileName.length() - 4);
                    if (ruleMap.containsKey(substring)) {
                        ErrorManager.errorManager.sendErrorMessage("§x§9§8§F§B§9§8[MythicChanger] §cError: Already loaded a rule config called: " +
                                fileName + "!");
                        continue;
                    }
                    ObjectSingleRule rule = new ObjectSingleRule(substring, YamlConfiguration.loadConfiguration(file));
                    ruleCaches.add(rule);
                    ruleMap.put(substring, rule);
                    Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fLoaded rule: " +
                            fileName + "!");
                }
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
        int amount = item.getAmount();
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
            item.setAmount(amount);
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
        return config.getBoolean(path, false);
    }

    public boolean getBoolean(String path, boolean defaultValue) {
        return config.getBoolean(path, defaultValue);
    }

    public String getString(String path, String... args) {
        String s = config.getString(path);
        if (s == null) {
            if (args.length == 0) {
                return null;
            }
            s = args[0];
        }
        for (int i = 1 ; i < args.length ; i += 2) {
            String var = "{" + args[i] + "}";
            if (args[i + 1] == null) {
                s = s.replace(var, "");
            }
            else {
                s = s.replace(var, args[i + 1]);
            }
        }
        return s.replace("{plugin_folder}", String.valueOf(MythicChanger.instance.getDataFolder()));
    }

}

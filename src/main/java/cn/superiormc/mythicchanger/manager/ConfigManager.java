package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.objects.ObjectSingleRule;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

public class ConfigManager {

    public static ConfigManager configManager;

    public Map<String, ObjectSingleRule> ruleMap = new TreeMap<>();

    public Collection<ObjectSingleRule> ruleCaches = new TreeSet<>();

    public ConfigManager() {
        configManager = this;
        initRulesConfigs();
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

    public ItemStack startFakeChange(ItemStack item, Player player) {
        if (item == null || item.getType().isAir()) {
            return item;
        }
        for (ObjectSingleRule rule: ruleCaches) {
            if (rule.getMatchItem(item)) {
                item = rule.setFakeChange(item, player);
            }
        }
        return item;
    }

    public void startRealChange(ItemStack item, Player player) {
        if (item == null || item.getType().isAir()) {
            return;
        }
        for (ObjectSingleRule rule: ruleCaches) {
            if (rule.getMatchItem(item)) {
                rule.setRealChange(item, player);
            }
        }
    }

    public ObjectSingleRule getRule(String id) {
        return ruleMap.get(id);
    }

}

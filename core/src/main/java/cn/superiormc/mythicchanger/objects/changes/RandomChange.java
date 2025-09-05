package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ChangesManager;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectAction;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomChange extends AbstractChangesRule {

    public RandomChange() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ConfigurationSection itemSection = singleChange.getConfigurationSection("random-change");
        if (itemSection == null) {
            return singleChange.getItem();
        }
        Map<String, Double> items = new HashMap<>();
        for (String itemKey : itemSection.getKeys(false)) {
            ConfigurationSection subSection = itemSection.getConfigurationSection(itemKey);
            items.put(itemKey, subSection.getDouble("rate", 1.0));
        }
        if (items.isEmpty()) {
            return singleChange.getItem();
        }
        String pickedSingleChange = selectRandom(items);
        if (pickedSingleChange == null) {
            return singleChange.getItem();
        }
        ConfigurationSection pickedSection = itemSection.getConfigurationSection(pickedSingleChange);
        if (pickedSection == null) {
            return singleChange.getItem();
        }
        ObjectSingleChange newSingleChange = new ObjectSingleChange(pickedSection, singleChange);
        if (singleChange.isFakeOrReal()) {
            return ChangesManager.changesManager.setFakeChange(newSingleChange);
        } else {
            return ChangesManager.changesManager.setRealChange(new ObjectAction(pickedSection.getConfigurationSection("actions")), newSingleChange);
        }
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("random-change", 1002);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("random-change") == null;
    }

    private String selectRandom(Map<String, Double> probabilityMap) {
        double totalWeight = 0.0;
        for (Double weight : probabilityMap.values()) {
            totalWeight += weight;
        }

        double randomValue = new Random().nextDouble() * totalWeight;
        for (Map.Entry<String, Double> entry : probabilityMap.entrySet()) {
            randomValue -= entry.getValue();
            if (randomValue <= 0) {
                return entry.getKey();
            }
        }
        return null; // 不应该到达这里
    }
}

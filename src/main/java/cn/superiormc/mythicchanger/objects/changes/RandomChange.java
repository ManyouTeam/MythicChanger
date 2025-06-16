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
        Map<ObjectSingleChange, Double> items = new HashMap<>();
        for (String itemKey : itemSection.getKeys(false)) {
            ObjectSingleChange newSingleChange = new ObjectSingleChange(itemSection.getConfigurationSection(itemKey), singleChange);
            items.put(newSingleChange, itemSection.getConfigurationSection(itemKey).getDouble("rate", 1.0));
        }
        if (items.isEmpty()) {
            return singleChange.getItem();
        }
        ObjectSingleChange pickedSingleChange = selectRandom(items);
        if (pickedSingleChange == null) {
            return singleChange.getItem();
        }
        if (singleChange.isFakeOrReal()) {
            return ChangesManager.changesManager.setFakeChange(pickedSingleChange);
        } else {
            return ChangesManager.changesManager.setRealChange(new ObjectAction(), pickedSingleChange);
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

    private ObjectSingleChange selectRandom(Map<ObjectSingleChange, Double> probabilityMap) {
        double totalWeight = 0.0;
        for (Double weight : probabilityMap.values()) {
            totalWeight += weight;
        }

        double randomValue = new Random().nextDouble() * totalWeight;
        for (Map.Entry<ObjectSingleChange, Double> entry : probabilityMap.entrySet()) {
            randomValue -= entry.getValue();
            if (randomValue <= 0) {
                return entry.getKey();
            }
        }
        return null; // 不应该到达这里
    }
}

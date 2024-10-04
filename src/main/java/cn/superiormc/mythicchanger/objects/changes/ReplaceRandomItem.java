package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.methods.BuildItem;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ReplaceRandomItem extends AbstractChangesRule {

    public ReplaceRandomItem() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section,
                               ItemStack original,
                               ItemStack item,
                               Player player,
                               boolean fakeOrReal,
                               boolean isPlayerInventory) {
        ConfigurationSection itemSection = section.getConfigurationSection("replace-random-item");
        Map<ItemStack, Double> items = new HashMap<>();
        for (String itemKey : itemSection.getKeys(false)) {
            ItemStack result = BuildItem.buildItemStack(itemSection.getConfigurationSection(itemKey));
            if (result.getType() == Material.BARRIER) {
                continue;
            }
            items.put(result, itemSection.getConfigurationSection(itemKey).getDouble("rate", 1.0));
        }
        if (items.isEmpty()) {
            return item;
        }
        return selectRandom(items);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("replace-random-item", 1001);
    }

    @Override
    public boolean getNeedRewriteItem(ConfigurationSection section) {
        return true;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("replace-random-item") == null;
    }

    private ItemStack selectRandom(Map<ItemStack, Double> probabilityMap) {
        double totalWeight = 0.0;
        for (Double weight : probabilityMap.values()) {
            totalWeight += weight;
        }

        double randomValue = new Random().nextDouble() * totalWeight;
        for (Map.Entry<ItemStack, Double> entry : probabilityMap.entrySet()) {
            randomValue -= entry.getValue();
            if (randomValue <= 0) {
                return entry.getKey();
            }
        }
        return null; // 不应该到达这里
    }
}

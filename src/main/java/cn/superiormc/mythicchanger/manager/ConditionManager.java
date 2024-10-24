package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.objects.conditions.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ConditionManager {

    public static ConditionManager conditionManager;

    private Map<String, AbstractCheckCondition> conditions;

    public ConditionManager() {
        conditionManager = this;
        initConditions();
    }

    private void initConditions() {
        conditions = new HashMap<>();
        registerNewCondition("biome", new ConditionBiome());
        registerNewCondition("permission", new ConditionPermission());
        registerNewCondition("placeholder", new ConditionPlaceholder());
        registerNewCondition("world", new ConditionWorld());
        registerNewCondition("any", new ConditionAny());
    }

    public void registerNewCondition(String actionID,
                                  AbstractCheckCondition condition) {
        if (!conditions.containsKey(actionID)) {
            conditions.put(actionID, condition);
        }
    }

    public boolean checkBoolean(ObjectSingleCondition condition, Player player, ItemStack original, ItemStack item) {
        for (AbstractCheckCondition checkCondition : conditions.values()) {
            String type = condition.getString("type");
            if (checkCondition.getType().equals(type) && !checkCondition.checkCondition(condition, player, original, item)) {
                return false;
            }
        }
        return true;
    }
}

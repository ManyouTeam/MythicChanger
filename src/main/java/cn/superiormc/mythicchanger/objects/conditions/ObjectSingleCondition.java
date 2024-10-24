package cn.superiormc.mythicchanger.objects.conditions;

import cn.superiormc.mythicchanger.manager.ConditionManager;
import cn.superiormc.mythicchanger.objects.AbstractSingleRun;
import cn.superiormc.mythicchanger.objects.ObjectCondition;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ObjectSingleCondition extends AbstractSingleRun {

    private final ObjectCondition condition;

    public ObjectSingleCondition(ObjectCondition condition, ConfigurationSection conditionSection) {
        super(conditionSection);
        this.condition = condition;
    }

    public boolean checkBoolean(Player player, ItemStack original, ItemStack item) {
        return ConditionManager.conditionManager.checkBoolean(this, player, original, item);
    }

    public ObjectCondition getCondition() {
        return condition;
    }

}

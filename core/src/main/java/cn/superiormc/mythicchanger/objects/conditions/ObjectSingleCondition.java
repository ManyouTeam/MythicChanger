package cn.superiormc.mythicchanger.objects.conditions;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConditionManager;
import cn.superiormc.mythicchanger.objects.AbstractSingleRun;
import cn.superiormc.mythicchanger.objects.ObjectAction;
import cn.superiormc.mythicchanger.objects.ObjectCondition;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ObjectSingleCondition extends AbstractSingleRun {

    private final ObjectCondition condition;

    private ObjectAction notMeetActions;

    private ObjectAction meetActions;

    public ObjectSingleCondition(ObjectCondition condition, ConfigurationSection conditionSection) {
        super(conditionSection);
        this.condition = condition;
        initActions();
    }

    private void initActions() {
        this.meetActions = new ObjectAction(section.getConfigurationSection("meet-actions"));
        this.notMeetActions = new ObjectAction(section.getConfigurationSection("not-meet-actions"));
    }

    public boolean checkBoolean(Player player, ItemStack original, ItemStack item) {
        boolean result = ConditionManager.conditionManager.checkBoolean(this, player, original, item);
        if (!MythicChanger.freeVersion) {
            if (result) {
                meetActions.runAllActions(player, original, item);
            } else {
                notMeetActions.runAllActions(player, original, item);
            }
        }
        return result;
    }

    public ObjectCondition getCondition() {
        return condition;
    }

}

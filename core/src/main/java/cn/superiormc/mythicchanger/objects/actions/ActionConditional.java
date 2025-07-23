package cn.superiormc.mythicchanger.objects.actions;

import cn.superiormc.mythicchanger.objects.ObjectAction;
import cn.superiormc.mythicchanger.objects.ObjectCondition;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActionConditional extends AbstractRunAction {

    public ActionConditional() {
        super("conditional");
        setRequiredArgs("actions", "conditions");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, ItemStack original, ItemStack item) {
        ConfigurationSection conditionSection = singleAction.getSection().getConfigurationSection("conditions");
        if (conditionSection == null) {
            return;
        }
        ObjectCondition condition = new ObjectCondition(conditionSection);
        if (!condition.getAllBoolean(player, original, item)) {
            return;
        }
        ConfigurationSection actionSection = singleAction.getSection().getConfigurationSection("actions");
        if (actionSection == null) {
            return;
        }
        ObjectAction action = new ObjectAction(actionSection);
        action.runAllActions(player, original, item);
    }
}

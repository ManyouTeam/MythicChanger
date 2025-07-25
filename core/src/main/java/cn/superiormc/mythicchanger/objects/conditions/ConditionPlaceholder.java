package cn.superiormc.mythicchanger.objects.conditions;

import cn.superiormc.mythicchanger.manager.ErrorManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ConditionPlaceholder extends AbstractCheckCondition {

    public ConditionPlaceholder() {
        super("placeholder");
        setRequiredArgs("placeholder", "rule", "value");
    }

    @Override
    protected boolean onCheckCondition(ObjectSingleCondition singleCondition, Player player, ItemStack original, ItemStack item) {
        String placeholder = singleCondition.getString("placeholder", player, original, item);
        if (placeholder.isEmpty()) {
            return true;
        }
        String value = singleCondition.getString("value", player, original, item);
        switch (singleCondition.getString("rule")) {
            case ">=":
                return Double.parseDouble(placeholder) >= Double.parseDouble(value);
            case ">":
                return Double.parseDouble(placeholder) > Double.parseDouble(value);
            case "=":
                return Double.parseDouble(placeholder) == Double.parseDouble(value);
            case "<":
                return Double.parseDouble(placeholder) < Double.parseDouble(value);
            case "<=":
                return Double.parseDouble(placeholder) <= Double.parseDouble(value);
            case "==":
                return placeholder.equals(value);
            case "!=":
                return !placeholder.equals(value);
            case "*=":
                return placeholder.contains(value);
            case "=*":
                return value.contains(placeholder);
            case "!*=":
                return !placeholder.contains(value);
            case "!=*":
                return !value.contains(placeholder);
            default:
                ErrorManager.errorManager.sendErrorMessage("§cError: Your placeholder condition can not being correctly load.");
                return true;
        }
    }
}

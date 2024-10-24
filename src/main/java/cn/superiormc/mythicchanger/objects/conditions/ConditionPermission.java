package cn.superiormc.mythicchanger.objects.conditions;


import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ConditionPermission extends AbstractCheckCondition {

    public ConditionPermission() {
        super("permission");
        setRequiredArgs("permission");
    }

    @Override
    protected boolean onCheckCondition(ObjectSingleCondition singleCondition, Player player, ItemStack original, ItemStack item) {
        return player.hasPermission(singleCondition.getString("permission"));
    }
}

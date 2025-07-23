package cn.superiormc.mythicchanger.objects.conditions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ConditionWorld extends AbstractCheckCondition {

    public ConditionWorld() {
        super("world");
        setRequiredArgs("world");
    }

    @Override
    protected boolean onCheckCondition(ObjectSingleCondition singleCondition, Player player, ItemStack original, ItemStack item) {
        return player.getWorld().getName().equals(singleCondition.getString("world", player, original, item));
    }
}

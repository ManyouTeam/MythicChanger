package cn.superiormc.mythicchanger.objects.conditions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ConditionBiome extends AbstractCheckCondition {

    public ConditionBiome() {
        super("biome");
        setRequiredArgs("biome");
    }

    @Override
    protected boolean onCheckCondition(ObjectSingleCondition singleCondition, Player player, ItemStack original, ItemStack item) {
        return player.getLocation().getBlock().getBiome().name().equals(singleCondition.getString("biome").toUpperCase());
    }
}

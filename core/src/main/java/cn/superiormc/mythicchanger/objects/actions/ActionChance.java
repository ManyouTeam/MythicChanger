package cn.superiormc.mythicchanger.objects.actions;

import cn.superiormc.mythicchanger.objects.ObjectAction;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActionChance extends AbstractRunAction {

    public ActionChance() {
        super("chance");
        setRequiredArgs("rate", "actions");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, ItemStack original, ItemStack item) {
        ConfigurationSection chanceSection = singleAction.getSection().getConfigurationSection("actions");
        if (chanceSection == null) {
            return;
        }
        double rate = singleAction.getDouble("rate", player, original, item);
        if (RandomUtils.nextDouble(0, 100) > rate) {
            ObjectAction action = new ObjectAction(chanceSection);
            action.runAllActions(player, original, item);
        }
    }
}

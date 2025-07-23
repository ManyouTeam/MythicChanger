package cn.superiormc.mythicchanger.objects.actions;

import cn.superiormc.mythicchanger.objects.ObjectAction;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActionAny extends AbstractRunAction {

    public ActionAny() {
        super("any");
        setRequiredArgs("actions");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, ItemStack original, ItemStack item) {
        ConfigurationSection chanceSection = singleAction.getSection().getConfigurationSection("actions");
        if (chanceSection == null) {
            return;
        }
        ObjectAction action = new ObjectAction(chanceSection);
        action.runRandomEveryActions(player, original, item, singleAction.getInt("amount", 1));
    }
}

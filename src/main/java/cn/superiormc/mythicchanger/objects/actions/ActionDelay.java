package cn.superiormc.mythicchanger.objects.actions;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.objects.ObjectAction;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActionDelay extends AbstractRunAction {

    public ActionDelay() {
        super("delay");
        setRequiredArgs("time", "actions");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, ItemStack original, ItemStack item) {
        ConfigurationSection chanceSection = singleAction.getSection().getConfigurationSection("actions");
        if (chanceSection == null) {
            return;
        }
        long time = singleAction.getSection().getLong("time");
        ObjectAction action = new ObjectAction(chanceSection);
        Bukkit.getScheduler().runTaskLater(MythicChanger.instance, () -> action.runAllActions(player, original, item), time);
    }
}

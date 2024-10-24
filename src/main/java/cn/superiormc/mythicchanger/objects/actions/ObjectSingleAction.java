package cn.superiormc.mythicchanger.objects.actions;

import cn.superiormc.mythicchanger.manager.ActionManager;
import cn.superiormc.mythicchanger.objects.AbstractSingleRun;
import cn.superiormc.mythicchanger.objects.ObjectAction;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ObjectSingleAction extends AbstractSingleRun {

    private final ObjectAction action;


    public ObjectSingleAction(ObjectAction action, ConfigurationSection actionSection) {
        super(actionSection);
        this.action = action;
    }

    public void doAction(Player player, ItemStack original, ItemStack item) {
        ActionManager.actionManager.doAction(this, player, original, item);
    }


    public ObjectAction getAction() {
        return action;
    }

}

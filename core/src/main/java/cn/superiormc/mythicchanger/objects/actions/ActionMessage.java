package cn.superiormc.mythicchanger.objects.actions;

import cn.superiormc.mythicchanger.MythicChanger;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActionMessage extends AbstractRunAction {

    public ActionMessage() {
        super("message");
        setRequiredArgs("message");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, ItemStack original, ItemStack item) {
        MythicChanger.methodUtil.sendMessage(player, singleAction.getString("message", player, original, item));
    }
}

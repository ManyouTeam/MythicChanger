package cn.superiormc.mythicchanger.objects.actions;

import cn.superiormc.mythicchanger.MythicChanger;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActionActionBar extends AbstractRunAction {

    public ActionActionBar() {
        super("action_bar");
        setRequiredArgs("message");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, ItemStack original, ItemStack item) {
        String msg = singleAction.getString("message", player, original, item);
        MythicChanger.methodUtil.sendActionBar(player, msg);
    }
}

package cn.superiormc.mythicchanger.objects.actions;

import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActionPlayerCommand extends AbstractRunAction {

    public ActionPlayerCommand() {
        super("player_command");
        setRequiredArgs("command");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, ItemStack original, ItemStack item) {
        CommonUtil.dispatchCommand(player, singleAction.getString("command", player, original, item));
    }
}

package cn.superiormc.mythicchanger.objects.actions;

import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActionOPCommand extends AbstractRunAction {

    public ActionOPCommand() {
        super("op_command");
        setRequiredArgs("command");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, ItemStack original, ItemStack item) {
        CommonUtil.dispatchOpCommand(player, singleAction.getString("command", player, original, item));
    }
}

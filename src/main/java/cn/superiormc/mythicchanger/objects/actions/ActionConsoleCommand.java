package cn.superiormc.mythicchanger.objects.actions;

import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActionConsoleCommand extends AbstractRunAction {

    public ActionConsoleCommand() {
        super("console_command");
        setRequiredArgs("command");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, ItemStack original, ItemStack item) {
        CommonUtil.dispatchCommand(singleAction.getString("command", player, original, item));
    }
}

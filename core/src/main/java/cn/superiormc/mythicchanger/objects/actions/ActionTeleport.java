package cn.superiormc.mythicchanger.objects.actions;

import cn.superiormc.mythicchanger.MythicChanger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActionTeleport extends AbstractRunAction {

    public ActionTeleport() {
        super("teleport");
        setRequiredArgs("world", "x", "y", "z");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, ItemStack original, ItemStack item) {
        Location loc = new Location(Bukkit.getWorld(singleAction.getString("world")),
                    singleAction.getDouble("x"),
                    singleAction.getDouble("y"),
                    singleAction.getDouble("z"),
                    singleAction.getInt("yaw", (int) player.getLocation().getYaw()),
                    singleAction.getInt("pitch", (int) player.getLocation().getPitch()));
        MythicChanger.methodUtil.playerTeleport(player, loc);
    }
}

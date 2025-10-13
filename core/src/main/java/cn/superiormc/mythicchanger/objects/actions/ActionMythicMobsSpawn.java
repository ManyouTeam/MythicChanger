package cn.superiormc.mythicchanger.objects.actions;

import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActionMythicMobsSpawn extends AbstractRunAction {

    public ActionMythicMobsSpawn() {
        super("mythicmobs_spawn");
        setRequiredArgs("entity");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, ItemStack original, ItemStack item) {
        String mobName = singleAction.getString("entity");
        String worldName = singleAction.getString("world");
        Location location;
        if (worldName == null) {
            location = player.getLocation();
        } else {
            World world = Bukkit.getWorld(worldName);
            location = new Location(world,
                    singleAction.getDouble("x", player, original, item),
                    singleAction.getDouble("y", player, original, item),
                    singleAction.getDouble("z", player, original, item));

        }
        CommonUtil.summonMythicMobs(location, mobName, singleAction.getInt("level", 1));
    }
}

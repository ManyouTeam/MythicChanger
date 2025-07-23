package cn.superiormc.mythicchanger.objects.actions;

import cn.superiormc.mythicchanger.MythicChanger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActionEntitySpawn extends AbstractRunAction {

    public ActionEntitySpawn() {
        super("entity_spawn");
        setRequiredArgs("entity");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, ItemStack original, ItemStack item) {
        EntityType entity = EntityType.valueOf(singleAction.getString("entity").toUpperCase());
        String worldName = singleAction.getString("world");
        Location location;
        if (worldName == null) {
            location = player.getLocation();
        } else {
            World world = Bukkit.getWorld(worldName);
            location = new Location(world,
                    singleAction.getDouble("x"),
                    singleAction.getDouble("y"),
                    singleAction.getDouble("z"));

        }
        MythicChanger.methodUtil.spawnEntity(location, entity);
    }
}

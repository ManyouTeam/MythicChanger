package cn.superiormc.mythicchanger.objects.actions;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActionSound extends AbstractRunAction {

    public ActionSound() {
        super("sound");
        setRequiredArgs("sound");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, ItemStack original, ItemStack item) {
        String soundName = singleAction.getString("sound");
        float volume = 1.0f;
        float pitch = 1.0f;
        if (singleAction.getSection().contains("volume")) {
            volume = Float.parseFloat(singleAction.getString("volume"));
        }
        if (singleAction.getSection().contains("pitch")) {
            pitch = Float.parseFloat(singleAction.getString("pitch"));
        }
        Location location = player.getLocation();
        if (soundName != null) {
            player.playSound(location, soundName, volume, pitch);
        }
    }
}

package cn.superiormc.mythicchanger.objects.actions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class ActionAnnouncement extends AbstractRunAction {

    public ActionAnnouncement() {
        super("announcement");
        setRequiredArgs("message");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, ItemStack original, ItemStack item) {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player p : players) {
            p.sendMessage(singleAction.getString("message", player, original, item));
        }
    }
}

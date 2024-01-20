package cn.superiormc.mythicchanger.hooks;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class MMOItemsReloadListener implements Listener {

    @EventHandler
    public void onReloadMMOItems(PlayerCommandPreprocessEvent event) {
        Bukkit.getConsoleSender().sendMessage(event.getMessage());
        if (event.getMessage().contains("mmoitems reload") || event.getMessage().contains("mi reload")) {
            MMOItemsHook.generateNewCache();
        }
    }
}

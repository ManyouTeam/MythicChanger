package cn.superiormc.mythicchanger.listeners;

import cn.superiormc.mythicchanger.protolcol.ProtocolLib.SetCursorItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerCacheListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        SetCursorItem.hashedStackMap.remove(event.getPlayer());
    }
}

package cn.superiormc.mythicchanger.listeners;

import cn.superiormc.mythicchanger.manager.ChangesManager;
import cn.superiormc.mythicchanger.protolcol.pacetevents.SetCursorItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerCacheListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        ChangesManager.changesManager.removeCooldown(event.getPlayer());
        SetCursorItem.hashedStackMap.remove(event.getPlayer().getUniqueId());
    }
}

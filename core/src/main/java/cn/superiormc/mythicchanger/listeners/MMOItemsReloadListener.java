package cn.superiormc.mythicchanger.listeners;

import cn.superiormc.mythicchanger.hooks.MMOItemsHook;
import cn.superiormc.mythicchanger.utils.SchedulerUtil;
import net.Indyuce.mmoitems.api.event.MMOItemsReloadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MMOItemsReloadListener implements Listener {

    @EventHandler
    public void onReloadMMOItems(MMOItemsReloadEvent event) {
        SchedulerUtil.runTaskLater(MMOItemsHook::generateNewCache, 1L);
    }
}

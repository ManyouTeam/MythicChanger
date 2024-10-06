package cn.superiormc.mythicchanger.listeners;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import com.ghostchu.quickshop.api.event.ItemPreviewComponentPrePopulateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class QuickShopListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onItemPreviewPreparing(final ItemPreviewComponentPrePopulateEvent event) {

        if (event.getPlayer() == null) {
            return;
        }
        final ItemStack item = event.getItemStack().clone();
        event.setItemStack(ConfigManager.configManager.startFakeChange(item, event.getPlayer(), true));
    }
}

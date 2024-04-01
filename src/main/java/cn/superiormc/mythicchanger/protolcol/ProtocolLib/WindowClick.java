package cn.superiormc.mythicchanger.protolcol.ProtocolLib;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class WindowClick implements Listener {

    // 客户端发给服务端
    public WindowClick() {
        if (ConfigManager.configManager.getBoolean("real-change-trigger.InventoryClickEvent")) {
            Bukkit.getPluginManager().registerEvents(this, MythicChanger.instance);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == null) {
            return;
        }
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        if (event.getClickedInventory().equals(player.getOpenInventory().getBottomInventory())) {
            ItemStack cursorItem = event.getCursor();
            ItemStack slotItem = event.getCurrentItem();
            if (isValid(cursorItem) && isValid(slotItem)) {
                ItemStack newItem = ConfigManager.configManager.startRealChange(slotItem, player).clone();
                event.setCurrentItem(cursorItem);
                Bukkit.getScheduler().runTask(MythicChanger.instance, () -> {
                    slotItem.setAmount(0);
                    player.setItemOnCursor(newItem);
                });
            } else if (isValid(cursorItem) && !isValid(slotItem)) {
                ItemStack newItem = ConfigManager.configManager.startRealChange(cursorItem, player);
                event.setCurrentItem(newItem);
                Bukkit.getScheduler().runTask(MythicChanger.instance, () -> {
                    player.getItemOnCursor().setAmount(0);
                });
            }
        }
    }

    private boolean isValid(ItemStack item) {
        return item != null && !item.getType().isAir();
    }
}
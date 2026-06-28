package cn.superiormc.mythicchanger.listeners;

import cn.superiormc.mythicchanger.gui.InvGUI;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.ErrorManager;
import cn.superiormc.mythicchanger.manager.ListenerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class GUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player player) {
            try {
                Inventory topInventory = e.getView().getTopInventory();
                InvGUI gui = topInventory.getHolder() instanceof InvGUI ? (InvGUI) topInventory.getHolder() : null;
                if (gui == null) {
                    return;
                }
                if (!topInventory.equals(gui.getInventory())) {
                    player.closeInventory();
                    ErrorManager.errorManager.sendErrorMessage("§cError: Found unregistered GUI Listener, now force close the inventory and then delete the excess GUI Listener. If this always heppens, please report to the plugin author.");
                    return;
                }
                if (!Objects.equals(e.getClickedInventory(), gui.getInventory())) {
                    if (e.getClick().isShiftClick() || e.getClick() == ClickType.DOUBLE_CLICK || ConfigManager.configManager.getBoolean("change-gui.ignore-click-outside", true)) {
                        e.setCancelled(true);
                    }
                    return;
                }
                if (e.getClick() == ClickType.DOUBLE_CLICK) {
                    e.setCancelled(true);
                    return;
                }
                if (gui.clickEventHandle(e.getClickedInventory(), e.getCursor(), e.getSlot())) {
                    e.setCancelled(true);
                }
                gui.afterClickEventHandle(e.getCursor(), e.getCurrentItem(), e.getSlot());
                if (e.getClick().toString().equals("SWAP_OFFHAND") && e.isCancelled()) {
                    player.getInventory().setItemInOffHand(player.getInventory().getItemInOffHand());
                }
            } catch (Exception ep) {
                ep.printStackTrace();
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (e.getWhoClicked() instanceof Player player) {
            Inventory topInventory = e.getView().getTopInventory();
            InvGUI gui = topInventory.getHolder() instanceof InvGUI ? (InvGUI) topInventory.getHolder() : null;
            if (gui == null) {
                return;
            }
            if (!topInventory.equals(gui.getInventory())) {
                player.closeInventory();
                ErrorManager.errorManager.sendErrorMessage("§cError: Found unregistered GUI Listener, now force close the inventory and then delete the excess GUI Listener. If this always heppens, please report to the plugin author.");
                return;
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player player) {
            Inventory topInventory = e.getView().getTopInventory();
            InvGUI gui = topInventory.getHolder() instanceof InvGUI ? (InvGUI) topInventory.getHolder() : null;
            if (gui == null) {
                return;
            }
            if (!Objects.equals(e.getInventory(), gui.getInventory())) {
                return;
            }
            gui.closeEventHandle(e.getInventory());
        }
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent e){
        Inventory topInventory = e.getPlayer().getOpenInventory().getTopInventory();
        if (topInventory.getHolder() instanceof InvGUI) {
            e.setCancelled(true);
        }
    }
}

package cn.superiormc.mythicchanger.listeners;

import cn.superiormc.mythicchanger.manager.ChangesManager;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.LanguageManager;
import cn.superiormc.mythicchanger.methods.Dupe;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class DupeListener implements Listener {

    @EventHandler
    public void onCheckChange(PlayerInventorySlotChangeEvent event) {
        ItemStack item = event.getNewItemStack();
        Player player = event.getPlayer();
        int slot = event.getSlot();
        if (ConfigManager.configManager.getBoolean("change-gui.anti-dupe-checker", false)) {
            if (Dupe.isGuiDisplayItem(item)) {
                player.getInventory().setItem(slot, null);
                LanguageManager.languageManager.sendStringText(player, "dupe-removed");
            }
            return;
        }
        if (ConfigManager.configManager.getBoolean("real-change-trigger.PlayerInventorySlotChangeEvent.enabled", false)) {
            if (ConfigManager.configManager.getBoolean("real-change-trigger.PlayerInventorySlotChangeEvent.ignore-equipment-when-open-other-invenotry", true) &&
                    !(player.getOpenInventory().getTopInventory() instanceof CraftingInventory)) {
                switch (slot) {
                    case 5:
                        if (player.getInventory().getItem(EquipmentSlot.HEAD).hashCode() == item.hashCode()) {
                            return;
                        }
                        break;
                    case 6:
                        if (player.getInventory().getItem(EquipmentSlot.CHEST).hashCode() == item.hashCode()) {
                            return;
                        }
                        break;
                    case 7:
                        if (player.getInventory().getItem(EquipmentSlot.LEGS).hashCode() == item.hashCode()) {
                            return;
                        }
                        break;
                    case 8:
                        if (player.getInventory().getItem(EquipmentSlot.FEET).hashCode() == item.hashCode()) {
                            return;
                        }
                        break;
                    case 40:
                        if (player.getInventory().getItem(EquipmentSlot.OFF_HAND).hashCode() == item.hashCode()) {
                            return;
                        }
                        break;
                }
            }
            if (ConfigManager.configManager.getBoolean("debug")) {
                TextUtil.sendMessage(null, TextUtil.pluginPrefix() + " §fPlayerInventorySlotChangeEvent found!");
            }
            if (ChangesManager.changesManager.getItemCooldown(player, slot)) {
                ChangesManager.changesManager.removeCooldown(player, slot);
            } else {
                ChangesManager.changesManager.addCooldown(player, slot);
                ItemStack newItem = ConfigManager.configManager.startRealChange(item, player);
                if (ItemUtil.isValid(newItem)) {
                    player.getInventory().setItem(slot, newItem);
                } else {
                    player.getInventory().setItem(slot, item);
                }
            }
        }
    }
}

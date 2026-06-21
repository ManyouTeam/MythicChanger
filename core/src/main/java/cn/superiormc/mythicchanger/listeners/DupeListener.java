package cn.superiormc.mythicchanger.listeners;

import cn.superiormc.mythicchanger.manager.ChangesManager;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.LanguageManager;
import cn.superiormc.mythicchanger.methods.Dupe;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
        if (ConfigManager.configManager.getBoolean("real-change-trigger.PlayerInventorySlotChangeEvent.enabled", true)) {
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

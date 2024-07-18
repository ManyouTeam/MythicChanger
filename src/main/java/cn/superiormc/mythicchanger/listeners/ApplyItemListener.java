package cn.superiormc.mythicchanger.listeners;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.LanguageManager;
import cn.superiormc.mythicchanger.objects.ObjectApplyItem;
import cn.superiormc.mythicchanger.objects.ObjectSingleRule;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ApplyItemListener implements Listener {


    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        if (!(event.getClickedInventory() instanceof PlayerInventory)) {
            return;
        }
        if (!event.getClick().isRightClick() && !event.getClick().isLeftClick()) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player)event.getWhoClicked();
        ItemStack targetItem = event.getCurrentItem();
        if (targetItem == null || targetItem.getType().isAir()) {
            return;
        }
        ItemStack extraItem = event.getCursor();
        if (extraItem == null || extraItem.getType().isAir()) {
            return;
        }
        ItemMeta meta = extraItem.getItemMeta();
        if (meta == null) {
            return;
        }
        if (!meta.getPersistentDataContainer().has(ObjectApplyItem.MYTHICCHANGER_APPLY_ITEM, PersistentDataType.STRING)) {
            return;
        }
        if (player.getGameMode() == GameMode.CREATIVE) {
            LanguageManager.languageManager.sendStringText(player, "error.creative-mode");
            return;
        }
        if (extraItem.getAmount() != 1) {
            LanguageManager.languageManager.sendStringText(player, "error.item-only-one");
            return;
        }
        String id = meta.getPersistentDataContainer().get(ObjectApplyItem.MYTHICCHANGER_APPLY_ITEM, PersistentDataType.STRING);
        ObjectApplyItem applyItem = ConfigManager.configManager.getApplyItem(id);
        if (applyItem != null && targetItem.getItemMeta() != null) {
            if (applyItem.matchItem(targetItem)) {
                ObjectSingleRule rule = applyItem.getRule();
                ItemMeta tempVal3 = targetItem.getItemMeta();
                if (rule != null) {
                    if (!rule.getCondition().getBoolean(player)) {
                        LanguageManager.languageManager.sendStringText(player, "not-meet-condition");
                    } else if (!tempVal3.getPersistentDataContainer().has(
                            ObjectApplyItem.MYTHICCHANGER_APPLY_RULE, PersistentDataType.STRING)) {
                        tempVal3.getPersistentDataContainer().set(ObjectApplyItem.MYTHICCHANGER_APPLY_RULE, PersistentDataType.STRING, rule.getId());
                        targetItem.setItemMeta(tempVal3);
                        LanguageManager.languageManager.sendStringText(player, "apply-item-success");
                        extraItem.setAmount(extraItem.getAmount() - 1);
                    } else {
                        LanguageManager.languageManager.sendStringText(player, "already-has-rule");
                    }
                } else if (applyItem.getDeapply()) {
                    if (tempVal3.getPersistentDataContainer().has(
                            ObjectApplyItem.MYTHICCHANGER_APPLY_RULE, PersistentDataType.STRING
                    )) {
                        tempVal3.getPersistentDataContainer().remove(ObjectApplyItem.MYTHICCHANGER_APPLY_RULE);
                        targetItem.setItemMeta(tempVal3);
                        LanguageManager.languageManager.sendStringText(player, "deapply-item-success");
                        extraItem.setAmount(extraItem.getAmount() - 1);
                    } else {
                        LanguageManager.languageManager.sendStringText(player, "do-not-has-rule");
                    }
                }
            } else {
                LanguageManager.languageManager.sendStringText(player, "do-not-apply");
            }
        }
    }
}

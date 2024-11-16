package cn.superiormc.mythicchanger.listeners;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.LanguageManager;
import cn.superiormc.mythicchanger.objects.ObjectApplyItem;
import cn.superiormc.mythicchanger.objects.ObjectSingleRule;
import cn.superiormc.mythicchanger.utils.ItemUtil;
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
        if (player.getGameMode() == GameMode.CREATIVE) {
            LanguageManager.languageManager.sendStringText(player, "error.creative-mode");
            return;
        }
        if (extraItem.getAmount() != 1) {
            LanguageManager.languageManager.sendStringText(player, "error.item-only-one");
            return;
        }
        ObjectApplyItem applyItem = ConfigManager.configManager.getApplyItemID(extraItem.getItemMeta());
        if (applyItem != null && targetItem.getItemMeta() != null) {
            if (applyItem.matchItem(player, targetItem)) {
                ObjectSingleRule rule = applyItem.getRule();
                ItemMeta tempVal3 = targetItem.getItemMeta();
                if (rule != null) {
                    if (!rule.getCondition().getAllBoolean(player, targetItem, targetItem)) {
                        LanguageManager.languageManager.sendStringText(player, "not-meet-condition");
                    } else if (ObjectApplyItem.getRule(tempVal3).size() >= ObjectApplyItem.getLimit(tempVal3)) {
                        LanguageManager.languageManager.sendStringText(player, "rule-limit-reached");
                    } else {
                        if (applyItem.getChance()) {
                            applyItem.doSuccessAction(player, targetItem);
                            targetItem = applyItem.addRuleID(targetItem, tempVal3);
                            if (applyItem.getApplyRealChange()) {
                                ItemStack newItem = rule.setRealChange(targetItem.clone(), targetItem, player);
                                if (ItemUtil.isValid(newItem) && !newItem.isSimilar(targetItem)) {
                                    targetItem.setAmount(0);
                                    event.setCurrentItem(newItem);
                                    event.setCancelled(true);
                                    player.updateInventory();
                                }
                            }
                        } else {
                            applyItem.doFailAction(player, targetItem);
                        }
                        extraItem.setAmount(extraItem.getAmount() - 1);
                    }
                } else {
                    if (applyItem.getChance()) {
                        applyItem.doSuccessAction(player, targetItem);
                        ItemStack newItem = applyItem.setRealChange(targetItem, targetItem.clone(), targetItem, player);
                        if (ItemUtil.isValid(newItem) && !newItem.isSimilar(targetItem)) {
                            targetItem.setAmount(0);
                            event.setCurrentItem(newItem);
                            event.setCancelled(true);
                            player.updateInventory();
                        }
                    } else {
                        applyItem.doFailAction(player, targetItem);
                        targetItem.setAmount(targetItem.getAmount() - 1);
                    }
                }
            } else {
                LanguageManager.languageManager.sendStringText(player, "do-not-apply");
            }
        }
    }
}

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
        ItemStack usedItemStack = event.getCurrentItem();
        if (usedItemStack == null || usedItemStack.getType().isAir()) {
            return;
        }
        ItemStack applyItemStack = event.getCursor();
        if (applyItemStack == null || applyItemStack.getType().isAir()) {
            return;
        }
        ItemMeta meta = applyItemStack.getItemMeta();
        if (meta == null) {
            return;
        }
        ObjectApplyItem applyItem = ConfigManager.configManager.getApplyItemID(applyItemStack.getItemMeta());
        if (applyItem != null && usedItemStack.getItemMeta() != null) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                LanguageManager.languageManager.sendStringText(player, "error.creative-mode");
                return;
            }
            if (applyItemStack.getAmount() != 1) {
                LanguageManager.languageManager.sendStringText(player, "error.item-only-one");
                return;
            }
            if (applyItem.matchItem(player, usedItemStack)) {
                ObjectSingleRule rule = applyItem.getRule();
                ItemMeta tempVal3 = usedItemStack.getItemMeta();
                if (rule != null) {
                    if (!rule.getCondition().getAllBoolean(player, usedItemStack, usedItemStack)) {
                        LanguageManager.languageManager.sendStringText(player, "not-meet-condition");
                    } else if (ObjectApplyItem.getRule(tempVal3).size() >= ObjectApplyItem.getLimit(tempVal3)) {
                        LanguageManager.languageManager.sendStringText(player, "rule-limit-reached");
                    } else {
                        if (applyItem.getChance()) {
                            applyItem.doSuccessAction(player, usedItemStack);
                            usedItemStack = applyItem.addRuleID(usedItemStack, tempVal3);
                            if (applyItem.getApplyRealChange()) {
                                ItemStack newItem = rule.setRealChange(usedItemStack, player);
                                if (ItemUtil.isValid(newItem) && !newItem.isSimilar(usedItemStack)) {
                                    usedItemStack.setAmount(0);
                                    event.setCurrentItem(newItem);
                                    event.setCancelled(true);
                                    player.updateInventory();
                                }
                            }
                        } else {
                            applyItem.doFailAction(player, usedItemStack);
                        }
                        applyItemStack.setAmount(applyItemStack.getAmount() - 1);
                    }
                } else {
                    if (applyItem.getChance()) {
                        applyItem.doSuccessAction(player, usedItemStack);
                        ItemStack newItem = applyItem.setRealChange(applyItemStack, usedItemStack, player);
                        if (ItemUtil.isValid(newItem) && !newItem.isSimilar(usedItemStack)) {
                            usedItemStack.setAmount(0);
                            event.setCurrentItem(newItem);
                            event.setCancelled(true);
                            player.updateInventory();
                        }
                    } else {
                        applyItem.doFailAction(player, usedItemStack);
                        applyItemStack.setAmount(applyItemStack.getAmount() - 1);
                    }
                }
            } else {
                LanguageManager.languageManager.sendStringText(player, "do-not-apply");
            }
        }
    }
}

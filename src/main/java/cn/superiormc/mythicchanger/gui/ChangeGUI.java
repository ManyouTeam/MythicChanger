package cn.superiormc.mythicchanger.gui;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.LanguageManager;
import cn.superiormc.mythicchanger.methods.BuildItem;
import cn.superiormc.mythicchanger.objects.ObjectApplyItem;
import cn.superiormc.mythicchanger.objects.ObjectSingleRule;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;


public class ChangeGUI extends InvGUI {

    private boolean success = false;

    public ChangeGUI(Player player) {
        super(player);
        constructGUI();
    }

    @Override
    protected void constructGUI() {
        if (Objects.isNull(inv)) {
            inv = Bukkit.createInventory(player, ConfigManager.configManager.getInt("change-gui.size", 54),
                    TextUtil.parse(ConfigManager.configManager.getString("change-gui.title"), player));
        }
        inv.setItem(ConfigManager.configManager.getInt("change-gui.confirm-slot", 2), BuildItem.buildItemStack(player,
                ConfigManager.configManager.getSection("change-gui.confirm-item")));
        ConfigurationSection customItemSection = ConfigManager.configManager.getSection("change-gui.custom-item");
        if (customItemSection != null) {
            for (String key : customItemSection.getKeys(false)){
                inv.setItem(Integer.parseInt(key), BuildItem.buildItemStack(player, customItemSection.getConfigurationSection(key)));
            }
        }
    }

    @Override
    public boolean clickEventHandle(Inventory inventory, ItemStack item, int slot) {
        if (slot == ConfigManager.configManager.getInt("change-gui.item-slot", 0)) {
            return false;
        }
        else if (slot == ConfigManager.configManager.getInt("change-gui.book-slot", 1)) {
            ItemStack targetItem = inv.getItem(ConfigManager.configManager.getInt("change-gui.book-slot", 0));
            if (targetItem != null && !targetItem.getType().isAir()) {
                return false;
            }
            if (item == null || item.getType().isAir()) {
                return true;
            }
            return ConfigManager.configManager.getApplyItemID(item.getItemMeta()) == null;
        }
        return true;
    }

    @Override
    public void afterClickEventHandle(ItemStack item, ItemStack currentItem, int slot) {
        if (slot != ConfigManager.configManager.getInt("change-gui.confirm-slot", 2)) {
            return;
        }
        success = true;
        ItemStack requireItem = inv.getItem(ConfigManager.configManager.getInt("change-gui.item-slot", 0));
        if (requireItem == null || requireItem.getType().isAir()) {
            success = false;
            return;
        }
        ItemStack targetItem = inv.getItem(ConfigManager.configManager.getInt("change-gui.book-slot", 1));
        if (targetItem == null || targetItem.getType().isAir()) {
            success = false;
            return;
        }
        ObjectApplyItem applyItem = ConfigManager.configManager.getApplyItemID(targetItem.getItemMeta());
        if (applyItem == null) {
            success = false;
            return;
        }
        ItemStack newItem = requireItem.clone();
        if (!applyItem.matchItem(newItem)) {
            success = false;
            player.closeInventory();
            LanguageManager.languageManager.sendStringText(player, "do-not-apply");
            return;
        }
        ObjectSingleRule rule = applyItem.getRule();
        ItemMeta tempVal3 = requireItem.getItemMeta();
        if (rule != null) {
            if (!rule.getCondition().getAllBoolean(player, item, item)) {
                success = false;
                if (ConfigManager.configManager.getBoolean("change-gui.close-if-fail")) {
                    player.closeInventory();
                }
                LanguageManager.languageManager.sendStringText(player, "not-meet-condition");
            } else if (ObjectApplyItem.getRule(tempVal3).size() >= ObjectApplyItem.getLimit(tempVal3)) {
                success = false;
                if (ConfigManager.configManager.getBoolean("change-gui.close-if-fail")) {
                    player.closeInventory();
                }
                LanguageManager.languageManager.sendStringText(player, "rule-limit-reached");
            } else {
                if (applyItem.getChance()) {
                    applyItem.doSuccessAction(player, requireItem);
                    requireItem = applyItem.addRuleID(requireItem, tempVal3);
                    if (applyItem.getApplyRealChange()) {
                        ItemStack changeItem = rule.setRealChange(requireItem.clone(), requireItem, player);
                        requireItem.setAmount(0);
                        inv.setItem(ConfigManager.configManager.getInt("change-gui.item-slot", 0),
                                changeItem);
                    }
                    LanguageManager.languageManager.sendStringText(player, "apply-item-success");
                } else {
                    applyItem.doFailAction(player, requireItem);
                }
                targetItem.setAmount(targetItem.getAmount() - 1);
            }
        } else if (applyItem.getDeapply()) {
            if (tempVal3.getPersistentDataContainer().has(
                    ObjectApplyItem.MYTHICCHANGER_APPLY_RULE, PersistentDataType.STRING
            )) {
                tempVal3.getPersistentDataContainer().remove(ObjectApplyItem.MYTHICCHANGER_APPLY_RULE);
                requireItem.setItemMeta(tempVal3);
                LanguageManager.languageManager.sendStringText(player, "deapply-item-success");
                targetItem.setAmount(targetItem.getAmount() - 1);
            } else {
                success = false;
                if (ConfigManager.configManager.getBoolean("change-gui.close-if-fail")) {
                    player.closeInventory();
                }
                LanguageManager.languageManager.sendStringText(player, "do-not-has-rule");
            }
        } else {
            success = false;
            if (ConfigManager.configManager.getBoolean("change-gui.close-if-fail")) {
                player.closeInventory();
            }
            LanguageManager.languageManager.sendStringText(player, "do-not-apply");
        }
        success = false;
    }

    @Override
    public void closeEventHandle(Inventory inventory) {
        if (!success) {
            ItemStack requireItem = inv.getItem(ConfigManager.configManager.getInt("change-gui.item-slot", 0));
            if (requireItem != null && !requireItem.getType().isAir()) {
                CommonUtil.giveOrDrop(player, requireItem);
            }
            ItemStack targetItem = inv.getItem(ConfigManager.configManager.getInt("change-gui.book-slot", 1));
            if (targetItem != null && !targetItem.getType().isAir()) {
                CommonUtil.giveOrDrop(player, targetItem);
            }
        }
    }


}

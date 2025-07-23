package cn.superiormc.mythicchanger.gui;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.LanguageManager;
import cn.superiormc.mythicchanger.methods.BuildItem;
import cn.superiormc.mythicchanger.objects.ObjectApplyItem;
import cn.superiormc.mythicchanger.objects.ObjectSingleRule;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
            inv = MythicChanger.methodUtil.createNewInv(player, ConfigManager.configManager.getInt("change-gui.size", 54),
                    ConfigManager.configManager.getString("change-gui.title"));
        }
        inv.setItem(ConfigManager.configManager.getInt("change-gui.confirm-slot", 2), BuildItem.buildItemStack(player,
                ConfigManager.configManager.getSection("change-gui.confirm-item")));
        ConfigurationSection customItemSection = ConfigManager.configManager.getSection("change-gui.custom-item");
        if (customItemSection != null) {
            for (String key : customItemSection.getKeys(false)){
                ConfigurationSection itemSection = customItemSection.getConfigurationSection(key);
                if (itemSection == null) {
                    continue;
                }
                itemSection.addDefault("name", null);
                itemSection.addDefault("lore", null);
                inv.setItem(Integer.parseInt(key), BuildItem.buildItemStack(player, itemSection));
            }
        }
    }

    @Override
    public boolean clickEventHandle(Inventory inventory, ItemStack item, int slot) {
        if (slot == ConfigManager.configManager.getInt("change-gui.item-slot", 0)) {
            return false;
        } else if (slot == ConfigManager.configManager.getInt("change-gui.book-slot", 1)) {
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
        ItemStack usedItemStack = inv.getItem(ConfigManager.configManager.getInt("change-gui.item-slot", 0));
        if (usedItemStack == null || usedItemStack.getType().isAir()) {
            success = false;
            return;
        }
        ItemStack applyItemStack = inv.getItem(ConfigManager.configManager.getInt("change-gui.book-slot", 1));
        if (applyItemStack == null || applyItemStack.getType().isAir()) {
            success = false;
            return;
        }
        ObjectApplyItem applyItem = ConfigManager.configManager.getApplyItemID(applyItemStack.getItemMeta());
        if (applyItem == null) {
            success = false;
            return;
        }
        ItemStack newItem = usedItemStack.clone();
        if (!applyItem.matchItem(player, newItem)) {
            success = false;
            player.closeInventory();
            LanguageManager.languageManager.sendStringText(player, "do-not-apply");
            return;
        }
        ObjectSingleRule rule = applyItem.getRule();
        ItemMeta tempVal3 = usedItemStack.getItemMeta();
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
                    applyItem.doSuccessAction(player, usedItemStack);
                    usedItemStack = applyItem.addRuleID(usedItemStack, tempVal3);
                    if (applyItem.getApplyRealChange()) {
                        ItemStack changeItem = rule.setRealChange(usedItemStack, player);
                        if (ItemUtil.isValid(changeItem)) {
                            usedItemStack.setAmount(0);
                            inv.setItem(ConfigManager.configManager.getInt("change-gui.item-slot", 0),
                                    changeItem);
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
                ItemStack changeItem = applyItem.setRealChange(applyItemStack, usedItemStack, player);
                if (ItemUtil.isValid(changeItem)) {
                    usedItemStack.setAmount(0);
                    inv.setItem(ConfigManager.configManager.getInt("change-gui.item-slot", 0),
                            changeItem);
                }
            } else {
                applyItem.doFailAction(player, usedItemStack);
                applyItemStack.setAmount(applyItemStack.getAmount() - 1);
            }
        }
        success = false;
    }

    @Override
    public void closeEventHandle(Inventory inventory) {
        if (!success) {
            ItemStack usedItemStack = inv.getItem(ConfigManager.configManager.getInt("change-gui.item-slot", 0));
            if (usedItemStack != null && !usedItemStack.getType().isAir()) {
                CommonUtil.giveOrDrop(player, usedItemStack);
            }
            ItemStack applyItemStack = inv.getItem(ConfigManager.configManager.getInt("change-gui.book-slot", 1));
            if (applyItemStack != null && !applyItemStack.getType().isAir()) {
                CommonUtil.giveOrDrop(player, applyItemStack);
            }
        }
    }


}

package cn.superiormc.mythicchanger.objects;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.*;
import cn.superiormc.mythicchanger.methods.BuildItem;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

public class ObjectApplyItem {

    public static final NamespacedKey MYTHICCHANGER_APPLY_ITEM = new NamespacedKey(MythicChanger.instance, "apply_item_id");

    public static final NamespacedKey MYTHICCHANGER_APPLY_LIMIT = new NamespacedKey(MythicChanger.instance, "apply_item_limit");

    public static final NamespacedKey MYTHICCHANGER_APPLY_RULE = new NamespacedKey(MythicChanger.instance, "rule_id");

    public static Collection<String> customModes = new ArrayList<>();

    private double chance;

    private final String id;

    private ObjectSingleRule rule;

    private final ConfigurationSection section;

    private final ObjectAction successAction;

    private final ObjectAction failAction;

    private final ObjectCondition condition;

    private final boolean hasFakeChanges;

    private String mode;

    public ObjectApplyItem(String id, ConfigurationSection section) {
        this.id = id;
        this.section = section;
        this.chance = section.getDouble("chance", 100);
        if (chance > 100) {
            chance = 100;
        } else if (chance < 0) {
            chance = 0;
        }
        this.successAction = new ObjectAction(section.getConfigurationSection("success-actions"));
        this.failAction = new ObjectAction(section.getConfigurationSection("fail-actions"));
        this.condition = new ObjectCondition(section.getConfigurationSection("conditions"));
        ConfigurationSection tempVal1 = section.getConfigurationSection("fake-changes");
        if (tempVal1 == null || tempVal1.getKeys(false).isEmpty()) {
            hasFakeChanges = false;
        } else {
            hasFakeChanges = true;
        }
        this.mode = section.getString("apply-item-mode", "GENERAL");
        if (!mode.equalsIgnoreCase("DRAG") && !mode.equalsIgnoreCase("GUI") && !mode.equalsIgnoreCase("GENERAL")) {
            customModes.add(mode);
        }
        String tempVal2 = section.getString("apply-rule");
        if (tempVal2 != null) {
            ObjectSingleRule tempVal3 = ConfigManager.configManager.getRule(tempVal2);
            if (tempVal3 != null) {
                rule = tempVal3;
            }
        }
    }

    public String getId() {
        return id;
    }

    public String getMode() {
        return mode;
    }

    public ObjectSingleRule getRule() {
        return rule;
    }

    public boolean hasFakeChanges() {
        return hasFakeChanges;
    }

    public boolean getApplyRealChange() {
        return section.getBoolean("apply-real-change", false);
    }

    public ItemStack getApplyItem(Player player) {
        ConfigurationSection itemSection = section.getConfigurationSection("display-item");
        if (itemSection == null) {
            itemSection = section;
        }
        ItemStack applyItem = BuildItem.buildItemStack(player, itemSection);
        ItemMeta meta = applyItem.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(MYTHICCHANGER_APPLY_ITEM, PersistentDataType.STRING, id);
            applyItem.setItemMeta(meta);
        } else {
            ErrorManager.errorManager.sendErrorMessage("§cFailed to generate apply item ID: " + id);
        }
        return applyItem;
    }

    public void giveApplyItem(Player player, int amount) {
        ItemStack targetItem = getApplyItem(player);
        Collection<ItemStack> result = new ArrayList<>();
        int leftAmount = 0;
        // leftAmount 代表物品玩家当前背包可以重复利用的堆叠数量
        for (ItemStack item : player.getInventory().getStorageContents()) {
            if (ItemUtil.isValid(item) && item.isSimilar(targetItem)) {
                leftAmount = leftAmount + targetItem.getMaxStackSize() - item.getAmount();
                if (leftAmount < 0) {
                    leftAmount = 0;
                }
            }
        }
        int requiredSlots = 0;
        if (amount > leftAmount) {
            requiredSlots = (int) Math.ceil((double) (amount - leftAmount) / targetItem.getMaxStackSize());
            boolean first = true;
            for (int i = 0 ; i < requiredSlots ; i ++) {
                if (first) {
                    ItemStack tempVal1 = targetItem.clone();
                    tempVal1.setAmount(amount - (requiredSlots - 1) * targetItem.getMaxStackSize());
                    result.add(tempVal1);
                    first = false;
                    continue;
                }
                ItemStack tempVal1 = targetItem.clone();
                tempVal1.setAmount(targetItem.getMaxStackSize());
                result.add(tempVal1);
            }
        } else {
            targetItem.setAmount(amount);
            result.add(targetItem);
        }
        for (ItemStack tempVal1 : result) {
            CommonUtil.giveOrDrop(player, tempVal1);
        }
        LanguageManager.languageManager.sendStringText(player, "plugin.item-gave", "item", id, "player", player.getName(), "amount", String.valueOf(amount));
    }

    public ItemStack setFakeChange(ItemStack item, Player player, boolean isPlayerInventory) {
        if (!hasFakeChanges || item == null || item.getType().isAir()) {
            return item;
        }
        return ChangesManager.changesManager.setFakeChange(section.getConfigurationSection("fake-changes"), item, player, isPlayerInventory);
    }

    public ItemStack setRealChange(ItemStack applyItem, ItemStack item, Player player) {
        if (item == null || item.getType().isAir()) {
            return null;
        }
        ConfigurationSection tempVal1 = section.getConfigurationSection("real-changes");
        if (tempVal1 == null || tempVal1.getKeys(false).isEmpty()) {
            return null;
        }
        applyItem.setAmount(applyItem.getAmount() - 1);
        return ChangesManager.changesManager.setRealChange(new ObjectAction(), tempVal1, item, player);
    }

    public boolean matchItem(Player player, ItemStack item) {
        return matchItem(player, item, "GENERAL");
    }

    public boolean matchItem(Player player, ItemStack item, String mode) {
        if (!this.mode.equalsIgnoreCase(mode) && !this.mode.equalsIgnoreCase("GENERAL")) {
            return false;
        }
        if (!condition.getAllBoolean(player, item, item)) {
            return false;
        }
        ConfigurationSection matchItemSection = section.getConfigurationSection("match-item");
        if (matchItemSection == null) {
            return true;
        } else {
            return MatchItemManager.matchItemManager.getMatch(matchItemSection, player, item);
        }
    }

    public ItemStack addRuleID(ItemStack item, ItemMeta meta) {
        if (meta == null) {
            return item;
        }
        String id = "";
        Collection<ObjectApplyItem> applyItems = getRule(meta);
        if (!applyItems.isEmpty()) {
            if (applyItems.contains(this)) {
                id = meta.getPersistentDataContainer().get(MYTHICCHANGER_APPLY_RULE, PersistentDataType.STRING) + ";;" + this.id;
            }
        } else {
            id = this.id;
        }
        meta.getPersistentDataContainer().set(MYTHICCHANGER_APPLY_RULE, PersistentDataType.STRING, id);
        item.setItemMeta(meta);
        return item;
    }

    public void doSuccessAction(Player player, ItemStack item) {
        successAction.runAllActions(player, item, item);
    }

    public void doFailAction(Player player, ItemStack item) {
        failAction.runAllActions(player, item, item);
    }

    public boolean getChance() {
        Random random = new Random();
        double rollNumber = random.nextDouble() * 100;
        return chance > rollNumber;
    }

    public static Collection<ObjectApplyItem> getRule(ItemMeta meta) {
        Collection<ObjectApplyItem> tempVal1 = new HashSet<>();
        if (meta == null) {
            return tempVal1;
        }
        if (meta.getPersistentDataContainer().has(MYTHICCHANGER_APPLY_RULE, PersistentDataType.STRING)) {
            String tempVal2 = meta.getPersistentDataContainer().get(MYTHICCHANGER_APPLY_RULE, PersistentDataType.STRING);
            if (tempVal2 == null) {
                return tempVal1;
            }
            for (String tempVal3 : tempVal2.split(";;")) {
                ObjectApplyItem applyItem = ConfigManager.configManager.getApplyItem(tempVal3);
                if (applyItem != null) {
                    tempVal1.add(applyItem);
                }
            }
        }
        return tempVal1;
    }

    public static int getLimit(ItemMeta meta) {
        if (meta == null) {
            return ConfigManager.configManager.getInt("default-apply-item-limit", 1);
        }
        if (meta.getPersistentDataContainer().has(MYTHICCHANGER_APPLY_LIMIT, PersistentDataType.INTEGER)) {
            return meta.getPersistentDataContainer().get(MYTHICCHANGER_APPLY_LIMIT, PersistentDataType.INTEGER);
        } else {
            return ConfigManager.configManager.getInt("default-apply-item-limit", 1);
        }
    }
    
}

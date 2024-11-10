package cn.superiormc.mythicchanger.objects;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.ErrorManager;
import cn.superiormc.mythicchanger.manager.MatchItemManager;
import cn.superiormc.mythicchanger.methods.BuildItem;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

public class ObjectApplyItem {

    public static final NamespacedKey MYTHICCHANGER_APPLY_ITEM = new NamespacedKey(MythicChanger.instance, "apply_item_id");

    public static final NamespacedKey MYTHICCHANGER_APPLY_LIMIT = new NamespacedKey(MythicChanger.instance, "apply_item_limit");

    public static final NamespacedKey MYTHICCHANGER_APPLY_RULE = new NamespacedKey(MythicChanger.instance, "rule_id");

    private double chance;

    private final String id;

    private ObjectSingleRule rule;

    private boolean deapply = false;

    private final ConfigurationSection section;

    private final ObjectAction successAction;

    private final ObjectAction failAction;

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
        String tempVal2 = section.getString("apply-rule");
        if (tempVal2 != null) {
            if (tempVal2.equals("deapply")) {
                deapply = true;
            }
            ObjectSingleRule tempVal1 = ConfigManager.configManager.getRule(tempVal2);
            if (tempVal1 != null) {
                rule = tempVal1;
            }
        }
    }

    public boolean getDeapply() {
        return deapply;
    }

    public String getId() {
        return id;
    }

    public ObjectSingleRule getRule() {
        return rule;
    }

    public boolean getApplyRealChange() {
        return section.getBoolean("apply-real-change", false);
    }

    public ItemStack getApplyItem(Player player) {
        ItemStack applyItem = BuildItem.buildItemStack(player, section);
        ItemMeta meta = applyItem.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(MYTHICCHANGER_APPLY_ITEM, PersistentDataType.STRING, id);
            applyItem.setItemMeta(meta);
        } else {
            ErrorManager.errorManager.sendErrorMessage("§x§9§8§F§B§9§8[MythicChanger] §cFailed to generate apply item ID: " + id);
        }
        return applyItem;
    }

    public boolean matchItem(ItemStack item) {
        ConfigurationSection matchItemSection = section.getConfigurationSection("match-item");
        if (matchItemSection == null) {
            return true;
        } else {
            return MatchItemManager.matchItemManager.getMatch(matchItemSection, item);
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
        if (chance > rollNumber) {
            return true;
        }
        return false;
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

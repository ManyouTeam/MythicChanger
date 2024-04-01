package cn.superiormc.mythicchanger.objects;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.ErrorManager;
import cn.superiormc.mythicchanger.manager.MatchItemManager;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectApplyItem {

    public static final NamespacedKey MYTHICCHANGER_APPLY_ITEM = new NamespacedKey(MythicChanger.instance, "apply_item_id");

    public static final NamespacedKey MYTHICCHANGER_APPLY_RULE = new NamespacedKey(MythicChanger.instance, "rule_id");

    private final ItemStack applyItem;

    private final String id;

    private ObjectSingleRule rule;

    private boolean deapply = false;

    private final ConfigurationSection section;

    public ObjectApplyItem(String id, ConfigurationSection section) {
        this.id = id;
        this.section = section;
        this.applyItem = ItemUtil.buildItemStack(section);
        if (applyItem != null) {
            ItemMeta meta = applyItem.getItemMeta();
            if (meta != null) {
                meta.getPersistentDataContainer().set(MYTHICCHANGER_APPLY_ITEM, PersistentDataType.STRING, id);
                applyItem.setItemMeta(meta);
            } else {
                ErrorManager.errorManager.sendErrorMessage("§x§9§8§F§B§9§8[MythicChanger] §cFailed to generate apply item ID: " + id);
            }
        }
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

    public ItemStack getApplyItem() {
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

    /*TODO: Multi support
    public static void addRuleID(ItemStack item, String ruleID) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        String id = "";
        if (meta.getPersistentDataContainer().has(MYTHICCHANGER_APPLY_RULE, PersistentDataType.STRING)) {
            id = meta.getPersistentDataContainer().get(MYTHICCHANGER_APPLY_RULE, PersistentDataType.STRING);
        }
        id = id + ";;" + ruleID;
        meta.getPersistentDataContainer().set(MYTHICCHANGER_APPLY_RULE, PersistentDataType.STRING, id);
        item.setItemMeta(meta);
    }

    public static List<String> getRuleID(ItemStack item) {
        List<String> tempVal1 = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return tempVal1;
        }
        String tempVal2;
        if (meta.getPersistentDataContainer().has(MYTHICCHANGER_APPLY_RULE, PersistentDataType.STRING)) {
            tempVal2 = meta.getPersistentDataContainer().get(MYTHICCHANGER_APPLY_RULE, PersistentDataType.STRING);
        } else {
            return tempVal1;
        }
        String[] tempVal3 = tempVal2.split(";;");
        Collections.addAll(tempVal1, tempVal3);
        return tempVal1;
    }
     */
    
}

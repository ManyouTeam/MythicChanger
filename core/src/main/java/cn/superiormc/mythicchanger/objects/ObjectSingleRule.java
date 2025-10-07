package cn.superiormc.mythicchanger.objects;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ChangesManager;
import cn.superiormc.mythicchanger.manager.MatchItemManager;
import org.bukkit.GameMode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ObjectSingleRule implements Comparable<ObjectSingleRule> {

    private final String id;

    private final YamlConfiguration config;

    private final ObjectAction action;

    private final ObjectCondition condition;

    public ObjectSingleRule(String id, YamlConfiguration config) {
        this.id = id;
        this.config = config;
        this.action = new ObjectAction(config.getConfigurationSection("real-change-actions"));
        this.condition = new ObjectCondition(config.getConfigurationSection("conditions"));
    }

    public boolean getMatchItem(ItemStack item, boolean fakeOrReal, Player player) {
        if (!MythicChanger.freeVersion && !condition.getAllBoolean(player, item, item)) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (fakeOrReal && item.hasItemMeta() && meta.getPersistentDataContainer().has(ObjectApplyItem.MYTHICCHANGER_APPLY_RULE,
                PersistentDataType.STRING)) {
            Collection<ObjectApplyItem> applyItems = ObjectApplyItem.getRule(meta);
            for (ObjectApplyItem applyItem : applyItems) {
                if (applyItem.getRule() != null && applyItem.getRule().id.equals(id)) {
                    return true;
                }
            }
        }
        ConfigurationSection section = config.getConfigurationSection("match-item");
        return MatchItemManager.matchItemManager.getMatch(section, player, item);
    }

    public ItemStack setFakeChange(ItemStack item, Player player, boolean isPlayerInventory) {
        if (item == null || item.getType().isAir()) {
            return item;
        }
        ConfigurationSection section = config.getConfigurationSection("fake-changes");
        if (section == null || section.getKeys(false).isEmpty()) {
            return item;
        }
        return ChangesManager.changesManager.setFakeChange(section, item, player, isPlayerInventory);
    }

    public ItemStack setRealChange(ItemStack item, Player player) {
        if (item == null || item.getType().isAir()) {
            return null;
        }
        ConfigurationSection section = config.getConfigurationSection("real-changes");
        if (section == null || section.getKeys(false).isEmpty()) {
            return null;
        }
        return ChangesManager.changesManager.setRealChange(action, section, item, player);
    }

    public String getId() {
        return id;
    }

    public int getWeight() {
        return config.getInt("weight", 0);
    }

    public ObjectAction getAction() {
        return action;
    }

    public ObjectCondition getCondition() {
        return condition;
    }

    public boolean getOnlyInPlayerInventory() {
        return config.getBoolean("only-in-player-inventory", false);
    }

    @Override
    public int compareTo(@NotNull ObjectSingleRule otherPrefix) {
        if (getWeight() == otherPrefix.getWeight()) {
            int len1 = getId().length();
            int len2 = otherPrefix.getId().length();
            int minLength = Math.min(len1, len2);

            for (int i = 0; i < minLength; i++) {
                char c1 = getId().charAt(i);
                char c2 = otherPrefix.getId().charAt(i);

                if (c1 != c2) {
                    if (Character.isDigit(c1) && Character.isDigit(c2)) {
                        // 如果字符都是数字，则按照数字大小进行比较
                        return Integer.compare(Integer.parseInt(getId().substring(i)), Integer.parseInt(otherPrefix.getId().substring(i)));
                    } else {
                        // 否则，按照字符的unicode值进行比较
                        return c1 - c2;
                    }
                }
            }

            return len1 - len2;
        }
        return getWeight() - otherPrefix.getWeight();
    }

    @Override
    public String toString() {
        return getId();
    }
}

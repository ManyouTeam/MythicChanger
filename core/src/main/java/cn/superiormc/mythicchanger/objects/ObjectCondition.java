package cn.superiormc.mythicchanger.objects;

import cn.superiormc.mythicchanger.objects.conditions.ObjectSingleCondition;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ObjectCondition {

    private ConfigurationSection section;

    private final List<ObjectSingleCondition> conditions = new ArrayList<>();

    public ObjectCondition() {
        this.section = new MemoryConfiguration();
    }

    public ObjectCondition(ConfigurationSection section) {
        this.section = section;
        initCondition();
    }

    private void initCondition() {
        if (section == null) {
            this.section = new MemoryConfiguration();
            return;
        }
        for (String key : section.getKeys(false)) {
            ConfigurationSection singleActionSection = section.getConfigurationSection(key);
            if (singleActionSection == null) {
                continue;
            }
            ObjectSingleCondition singleAction = new ObjectSingleCondition(this, singleActionSection);
            conditions.add(singleAction);
        }
    }

    public boolean getAllBoolean(Player player, ItemStack original, ItemStack item) {
        if (player == null) {
            return false;
        }
        for (ObjectSingleCondition singleCondition : conditions){
            if (!singleCondition.checkBoolean(player, original, item)) {
                return false;
            }
        }
        return true;
    }

    public boolean getAnyBoolean(Player player, ItemStack original, ItemStack item) {
        if (player == null) {
            return false;
        }
        for (ObjectSingleCondition singleCondition : conditions){
            if (singleCondition.checkBoolean(player, original, item)) {
                return true;
            }
        }
        return false;
    }
}

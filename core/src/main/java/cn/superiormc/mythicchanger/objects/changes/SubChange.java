package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ChangesManager;
import cn.superiormc.mythicchanger.manager.MatchItemManager;
import cn.superiormc.mythicchanger.objects.ObjectAction;
import cn.superiormc.mythicchanger.objects.ObjectCondition;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class SubChange extends AbstractChangesRule {

    public SubChange() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ConfigurationSection itemSection = singleChange.getConfigurationSection("sub-change");
        if (itemSection == null) {
            return singleChange.getItem();
        }
        ItemStack result = null;
        for (String itemKey : itemSection.getKeys(false)) {
            ConfigurationSection subSection = itemSection.getConfigurationSection(itemKey);
            if (subSection == null) {
                continue;
            }
            if (!MatchItemManager.matchItemManager.getMatch(subSection.getConfigurationSection("match-item"), singleChange.getPlayer(), singleChange.getItem())) {
                continue;
            }
            ObjectCondition condition = new ObjectCondition(subSection.getConfigurationSection("conditions"));
            if (!condition.getAllBoolean(singleChange.getPlayer(), singleChange.getOriginal(), singleChange.getItem())) {
                continue;
            }
            ObjectSingleChange newSingleChange = new ObjectSingleChange(subSection.getConfigurationSection("changes"), singleChange);
            if (singleChange.isFakeOrReal()) {
                result = ChangesManager.changesManager.setFakeChange(newSingleChange);
            } else {
                result = ChangesManager.changesManager.setRealChange(new ObjectAction(subSection.getConfigurationSection("actions")), newSingleChange);
            }
            if (singleChange.getBoolean("sub-change-match-one")) {
                break;
            }
        }
        if (result == null) {
            return singleChange.getItem();
        }
        return result;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("sub-change");
    }
}

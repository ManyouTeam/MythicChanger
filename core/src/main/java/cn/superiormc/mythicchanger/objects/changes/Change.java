package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ChangesManager;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.ErrorManager;
import cn.superiormc.mythicchanger.objects.ObjectAction;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Change extends AbstractChangesRule {

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        List<String> changeIDs = getChangeIDs(singleChange.get("change"));
        ObjectSingleChange currentChange = singleChange;
        for (String changeID : changeIDs) {
            ConfigurationSection section = ConfigManager.configManager.getChange(changeID);
            if (section == null) {
                ErrorManager.errorManager.sendErrorMessage("§cError: Can not find change config: " + changeID + "!");
                continue;
            }
            if (!currentChange.enterChange(changeID)) {
                ErrorManager.errorManager.sendErrorMessage("§cError: Circular change config reference detected: " + changeID + "!");
                continue;
            }
            try {
                ObjectSingleChange nestedChange = new ObjectSingleChange(section, currentChange);
                if (singleChange.isFakeOrReal()) {
                    ChangesManager.changesManager.setFakeChange(nestedChange);
                } else {
                    ChangesManager.changesManager.setRealChange(new ObjectAction(), nestedChange);
                }
                currentChange = nestedChange;
            } finally {
                singleChange.exitChange(changeID);
            }
        }
        return currentChange.getItem();
    }

    private List<String> getChangeIDs(Object configuredChanges) {
        List<String> result = new ArrayList<>();
        if (configuredChanges instanceof List<?>) {
            for (Object configuredChange : (List<?>) configuredChanges) {
                if (configuredChange != null && !configuredChange.toString().isEmpty()) {
                    result.add(configuredChange.toString());
                }
            }
        } else if (configuredChanges != null && !configuredChanges.toString().isEmpty()) {
            result.add(configuredChanges.toString());
        }
        return result;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("change");
    }
}

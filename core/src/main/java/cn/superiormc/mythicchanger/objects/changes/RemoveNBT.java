package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RemoveNBT extends AbstractChangesRule {

    public RemoveNBT() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        NBTItem nbtItem = new NBTItem(singleChange.getItem().clone());
        List<String> tempVal1 = singleChange.getStringList("remove-nbt");
        big: for (String key : tempVal1) {
            String[] parentKeys = key.split(";;");
            if (parentKeys.length == 1) {
                nbtItem.removeKey(parentKeys[0]);
            } else if (parentKeys.length > 1) {
                NBTCompound nbtCompound = null;
                String lastElement = parentKeys[parentKeys.length - 1];
                String[] newArray = new String[parentKeys.length - 1];
                System.arraycopy(parentKeys, 0, newArray, 0, parentKeys.length - 1);
                parentKeys = newArray;
                for (String parentKey : parentKeys) {
                    if (parentKey.isEmpty()) {
                        continue;
                    }
                    if (nbtCompound == null && nbtItem.getCompound(parentKey) != null) {
                        nbtCompound = nbtItem.getCompound(parentKey);
                    } else if (nbtCompound != null && nbtCompound.getCompound(parentKey) != null) {
                        nbtCompound = nbtCompound.getCompound(parentKey);
                    } else {
                        continue big;
                    }
                }
                if (nbtCompound != null) {
                    nbtCompound.removeKey(lastElement);
                    if (nbtCompound.getKeys().isEmpty() && nbtCompound.getParent() != null) {
                        nbtCompound.getParent().removeKey(nbtCompound.getName());
                    }
                }
            }
        }
        return nbtItem.getItem();
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("remove-nbt", -199);
    }

    @Override
    public boolean getNeedRewriteItem(ConfigurationSection section) {
        return true;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("remove-nbt");
    }
}

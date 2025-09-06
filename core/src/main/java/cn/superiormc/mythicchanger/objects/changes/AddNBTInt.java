package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.MathUtil;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AddNBTInt extends AbstractChangesRule {

    public AddNBTInt() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        NBTItem nbtItem = new NBTItem(singleChange.getItem().clone());
        List<String> tempVal1 = singleChange.getStringList("add-nbt-int");
        for (String key : tempVal1) {
            String[] parentKeys = key.split(";;");
            if (parentKeys.length == 2) {
                nbtItem.setInteger(parentKeys[0], MathUtil.doCalculate(parentKeys[1]).intValue());
            } else if (parentKeys.length > 2) {
                NBTCompound nbtCompound = null;
                String lastElement = parentKeys[parentKeys.length - 1];
                String last2Element = parentKeys[parentKeys.length - 2];
                String[] newArray = new String[parentKeys.length - 2];
                System.arraycopy(parentKeys, 0, newArray, 0, parentKeys.length - 2);
                parentKeys = newArray;
                for (String parentKey : parentKeys) {
                    if (parentKey.isEmpty()) {
                        continue;
                    }
                    if (nbtCompound == null) {
                        nbtCompound = nbtItem.getOrCreateCompound(parentKey);
                    } else {
                        nbtCompound = nbtCompound.getOrCreateCompound(parentKey);
                    }
                }
                if (nbtCompound != null) {
                    nbtCompound.setInteger(last2Element, MathUtil.doCalculate(lastElement).intValue());
                }
            }
        }
        singleChange.setNeedRewriteItem();
        return nbtItem.getItem();
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("add-nbt-int", 201);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("add-nbt-int");
    }
}

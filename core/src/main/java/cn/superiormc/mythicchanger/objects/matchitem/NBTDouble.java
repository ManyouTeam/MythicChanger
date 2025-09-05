package cn.superiormc.mythicchanger.objects.matchitem;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class NBTDouble extends AbstractMatchItemRule {

    public NBTDouble() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        NBTItem nbtItem = new NBTItem(item);
        List<String> tempVal1 = section.getStringList("nbt-double");
        for (String key : tempVal1) {
            String[] parentKeys = key.split(";;");
            if (parentKeys.length == 3 && nbtItem.hasTag(parentKeys[0], NBTType.NBTTagDouble) && getResult(parentKeys[2], parentKeys[1], parentKeys[0], nbtItem)) {
                return true;
            } else if (parentKeys.length > 3) {
                NBTCompound nbtCompound = null;
                String lastElement = parentKeys[parentKeys.length - 1];
                String last2Element = parentKeys[parentKeys.length - 2];
                String last3Element = parentKeys[parentKeys.length - 3];
                String[] newArray = new String[parentKeys.length - 3];
                System.arraycopy(parentKeys, 0, newArray, 0, parentKeys.length - 3);
                parentKeys = newArray;
                for (String parentKey : parentKeys) {
                    if (nbtCompound == null) {
                        nbtCompound = nbtItem.getCompound(parentKey);
                    } else if (nbtCompound.getCompound(parentKey) != null){
                        nbtCompound = nbtCompound.getCompound(parentKey);
                    } else if (nbtCompound.hasTag(last3Element, NBTType.NBTTagDouble) && getResult(lastElement, last2Element, last3Element, nbtCompound)) {
                        return true;
                    }
                }
                if (nbtCompound != null && nbtCompound.hasTag(last3Element, NBTType.NBTTagDouble) && getResult(lastElement, last2Element, last3Element, nbtCompound)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean getResult(String lastElement, String last2Element, String last3Element, NBTCompound nbtCompound) {
        switch (last2Element) {
            case ">=":
                return nbtCompound.getDouble(last3Element) >= Double.parseDouble(lastElement);
            case ">":
                return nbtCompound.getDouble(last3Element) > Double.parseDouble(lastElement);
            case "<=":
                return nbtCompound.getDouble(last3Element) <= Double.parseDouble(lastElement);
            case "<":
                return nbtCompound.getDouble(last3Element) < Double.parseDouble(lastElement);
            case "==":
                return nbtCompound.getDouble(last3Element) == Double.parseDouble(lastElement);
        }
        return false;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("nbt-double");
    }
}

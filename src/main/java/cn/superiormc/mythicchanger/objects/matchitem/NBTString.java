package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.utils.CommonUtil;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class NBTString extends AbstractMatchItemRule {
    public NBTString() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item) {
        if (item == null) {
            return false;
        }
        if (section.get("nbt-string") == null) {
            return true;
        }
        if (!CommonUtil.checkPluginLoad("NBTAPI")) {
            return false;
        }
        NBTItem nbtItem = new NBTItem(item);
        List<String> tempVal1 = section.getStringList("nbt-string");
        for (String key : tempVal1) {
            String[] parentKeys = key.split(";;");
            if (parentKeys.length == 2 && nbtItem.hasTag(parentKeys[0], NBTType.NBTTagString) && nbtItem.getString(parentKeys[0]).equals(parentKeys[1])) {
                return true;
            } else if (parentKeys.length > 2) {
                NBTCompound nbtCompound = null;
                String lastElement = parentKeys[parentKeys.length - 1];
                String last2Element = parentKeys[parentKeys.length - 2];
                String[] newArray = new String[parentKeys.length - 2];
                System.arraycopy(parentKeys, 0, newArray, 0, parentKeys.length - 2);
                parentKeys = newArray;
                for (String parentKey : parentKeys) {
                    if (nbtCompound == null) {
                        nbtCompound = nbtItem.getCompound(parentKey);
                    } else if (nbtCompound.getCompound(parentKey) != null){
                        nbtCompound = nbtCompound.getCompound(parentKey);
                    } else if (nbtCompound.hasTag(last2Element, NBTType.NBTTagString) && nbtCompound.getString(last2Element).equals(lastElement)) {
                        return true;
                    }
                }
                if (nbtCompound != null && nbtCompound.hasTag(last2Element, NBTType.NBTTagString) && nbtCompound.getString(last2Element).equals(lastElement)) {
                    return true;
                }
            }
        }
        return false;
    }
}

package cn.superiormc.mythicchanger.utils;

import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTType;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.inventory.ItemStack;

public class NBTUtil {

    public static Object parseNBT(ItemStack item, String key) {
        if (!CommonUtil.checkPluginLoad("NBTAPI")) {
            return null;
        }
        ReadWriteNBT nbtCompound = new NBTItem(item);
        if (nbtCompound.hasTag(key, NBTType.NBTTagByte)) {
            return nbtCompound.getByte(key);
        } else if (nbtCompound.hasTag(key, NBTType.NBTTagShort)) {
            return nbtCompound.getShort(key);
        } else if (nbtCompound.hasTag(key, NBTType.NBTTagInt)) {
            return nbtCompound.getInteger(key);
        } else if (nbtCompound.hasTag(key, NBTType.NBTTagLong)) {
            return nbtCompound.getLong(key);
        } else if (nbtCompound.hasTag(key, NBTType.NBTTagFloat)) {
            return nbtCompound.getFloat(key);
        } else if (nbtCompound.hasTag(key, NBTType.NBTTagDouble)) {
            return nbtCompound.getDouble(key);
        } else if (nbtCompound.hasTag(key, NBTType.NBTTagString)) {
            return nbtCompound.getString(key);
        }
        return null;
    }

}


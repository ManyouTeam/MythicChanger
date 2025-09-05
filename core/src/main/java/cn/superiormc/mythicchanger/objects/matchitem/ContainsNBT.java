package cn.superiormc.mythicchanger.objects.matchitem;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ContainsNBT extends AbstractMatchItemRule {

    public ContainsNBT() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        NBTItem nbtItem = new NBTItem(item);
        List<String> tempVal1 = section.getStringList("contains-nbt");
        for (String key : tempVal1) {
            String[] parentKeys = key.split(";;");
            if (parentKeys.length == 1 && nbtItem.hasTag(key)) {
                return true;
            } else {
                NBTCompound nbtCompound = null;
                String lastElement = parentKeys[parentKeys.length - 1];
                String[] newArray = new String[parentKeys.length - 1];
                System.arraycopy(parentKeys, 0, newArray, 0, parentKeys.length - 1);
                parentKeys = newArray;
                for (String parentKey : parentKeys) {
                    if (nbtCompound == null) {
                        nbtCompound = nbtItem.getCompound(parentKey);
                    } else if (nbtCompound.getCompound(parentKey) != null){
                        nbtCompound = nbtCompound.getCompound(parentKey);
                    } else if (nbtCompound.hasTag(lastElement)) {
                        return true;
                    }
                }
                if (nbtCompound != null && nbtCompound.hasTag(lastElement)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("contains-nbt") == null;
    }
}

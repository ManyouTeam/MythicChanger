package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.utils.CommonUtil;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

;

public class AddNBTInt extends AbstractChangesRule {

    public AddNBTInt() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack item, Player player, boolean fakeOrReal) {
        if (section.get("add-nbt-int") == null) {
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }
        if (!CommonUtil.checkPluginLoad("NBTAPI")) {
            return item;
        }
        NBTItem nbtItem = new NBTItem(item.clone());
        List<String> tempVal1 = section.getStringList("add-nbt-int");
        for (String key : tempVal1) {
            String[] parentKeys = key.split(";;");
            if (parentKeys.length == 2) {
                nbtItem.setInteger(parentKeys[0], Integer.parseInt(parentKeys[1]));
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
                    nbtCompound.setInteger(last2Element, Integer.parseInt(lastElement));
                }
            }
        }
        return nbtItem.getItem();
    }

    @Override
    public int getWeight() {
        return 201;
    }

    @Override
    public boolean getNeedRewriteItem() {
        return true;
    }
}

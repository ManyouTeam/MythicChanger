package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

;

public class AddNBTByte extends AbstractChangesRule {

    public AddNBTByte() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack original, ItemStack item, Player player, boolean fakeOrReal) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }
        NBTItem nbtItem = new NBTItem(item.clone());
        List<String> tempVal1 = section.getStringList("add-nbt-byte");
        for (String key : tempVal1) {
            String[] parentKeys = key.split(";;");
            if (parentKeys.length == 2) {
                nbtItem.setByte(parentKeys[0], Byte.parseByte(parentKeys[1]));
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
                    nbtCompound.setByte(last2Element, Byte.parseByte(lastElement));
                }
            }
        }
        return nbtItem.getItem();
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("add-nbt-byte", 203);
    }

    @Override
    public boolean getNeedRewriteItem(ConfigurationSection section) {
        return true;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("add-nbt-byte") == null;
    }
}

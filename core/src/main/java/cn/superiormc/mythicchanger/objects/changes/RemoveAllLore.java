package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RemoveAllLore extends AbstractChangesRule {

    public RemoveAllLore() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (!singleChange.getBoolean("remove-all-lore")) {
            return singleChange.getItem();
        }
        ItemMeta meta = singleChange.getItemMeta();
        if (meta.hasLore()) {
            MythicChanger.methodUtil.setItemLore(meta, null, singleChange.getPlayer());
        }
        singleChange.setItemMeta(meta);
        return singleChange.getItem();
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("remove-all-lore");
    }
}

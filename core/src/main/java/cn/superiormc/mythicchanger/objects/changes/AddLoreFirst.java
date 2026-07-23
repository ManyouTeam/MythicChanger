package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AddLoreFirst extends AbstractChangesRule {

    public AddLoreFirst() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ItemMeta meta = singleChange.getItemMeta();
        List<String> itemLore = singleChange.getStringList("add-lore-first");
        if (meta.hasLore()) {
            itemLore.addAll(MythicChanger.methodUtil.getItemLore(meta));
        }
        MythicChanger.methodUtil.setItemLore(meta, itemLore, singleChange.getPlayer());
        return singleChange.setItemMeta(meta);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("add-lore-first").isEmpty();
    }
}

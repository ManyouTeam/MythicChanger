package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MIUpdateLore extends AbstractChangesRule {

    public MIUpdateLore() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        String itemName = ItemUtil.getItemName(singleChange.getItem());
        if (singleChange.getBoolean("mi-update-lore")) {
            MMOItem mmoItem = new LiveMMOItem(singleChange.getItem());
            ItemStack result = mmoItem.newBuilder().build();
            ItemMeta meta = result.getItemMeta();
            if (meta == null) {
                return singleChange.getItem();
            }
            if (meta.hasDisplayName()) {
                meta.setDisplayName(itemName);
            }
            result.setItemMeta(meta);
            return result;
        }
        return singleChange.getItem();
    }

    @Override
    public boolean getNeedRewriteItem(ConfigurationSection section) {
        return true;
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("mi-update-lore", 115);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("mi-update-lore");
    }
}

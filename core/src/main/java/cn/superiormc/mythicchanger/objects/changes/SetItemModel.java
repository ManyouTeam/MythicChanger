package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetItemModel extends AbstractChangesRule {

    public SetItemModel() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        String itemModel = singleChange.getString("set-item-model");
        if (itemModel == null || itemModel.isEmpty()) {
            return singleChange.getItem();
        }
        ItemMeta meta = singleChange.getItemMeta();
        meta.setItemModel(CommonUtil.parseNamespacedKey(itemModel));
        return singleChange.setItemMeta(meta);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("set-item-model");
    }
}

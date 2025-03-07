package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.objects.changes.AbstractChangesRule;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import com.google.common.base.Enums;
import com.google.common.collect.MultimapBuilder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AddFlags extends AbstractChangesRule {

    public AddFlags() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ItemMeta meta = singleChange.getItemMeta();
        for (String flag : singleChange.getStringList("add-flags")) {
            flag = flag.toUpperCase();
            ItemFlag itemFlag = Enums.getIfPresent(ItemFlag.class, flag).orNull();
            if (itemFlag != null) {
                meta.addItemFlags(itemFlag);
            }
            if (CommonUtil.getMinorVersion(20, 6) && itemFlag == ItemFlag.HIDE_ATTRIBUTES && meta.getAttributeModifiers() == null) {
                meta.setAttributeModifiers(MultimapBuilder.hashKeys().hashSetValues().build());
            }
        }
        return singleChange.setItemMeta(meta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("add-flags", 3);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("add-flags").isEmpty();
    }

}

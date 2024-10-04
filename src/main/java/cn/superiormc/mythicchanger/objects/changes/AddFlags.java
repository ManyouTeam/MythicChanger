package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
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
    public ItemStack setChange(ConfigurationSection section,
                               ItemStack original,
                               ItemStack item,
                               Player player,
                               boolean fakeOrReal,
                               boolean isPlayerInventory) {
        ItemMeta meta = item.getItemMeta();
        for (String flag : section.getStringList("add-flags")) {
            flag = flag.toUpperCase();
            ItemFlag itemFlag = Enums.getIfPresent(ItemFlag.class, flag).orNull();
            if (itemFlag != null) {
                meta.addItemFlags(itemFlag);
            }
            if (CommonUtil.getMinorVersion(20, 6) && itemFlag == ItemFlag.HIDE_ATTRIBUTES && meta.getAttributeModifiers() == null) {
                meta.setAttributeModifiers(MultimapBuilder.hashKeys().hashSetValues().build());
            }
        }
        item.setItemMeta(meta);
        return item;
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

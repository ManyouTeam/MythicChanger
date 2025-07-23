package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import com.google.common.collect.MultimapBuilder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FixHideAttributes extends AbstractChangesRule {

    public FixHideAttributes() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ItemMeta meta = singleChange.getItemMeta();
        if (CommonUtil.getMinorVersion(20, 6) && meta.getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)
                && meta.getAttributeModifiers() == null) {
            meta.setAttributeModifiers(MultimapBuilder.hashKeys().hashSetValues().build());
        }
        return singleChange.setItemMeta(meta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("fix-hide-attributes", 1500);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.getBoolean("fix-hide-attributes");
    }
}

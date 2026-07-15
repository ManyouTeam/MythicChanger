package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.AttributeUtil;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import com.google.common.base.Enums;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class RemoveAttributes extends AbstractChangesRule {

    public RemoveAttributes() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ItemMeta meta = singleChange.getItemMeta();
        Set<Attribute> attributes = new HashSet<>();
        for (String attribute : singleChange.getStringList("remove-attributes")) {
            Attribute attributeInst;
            if (CommonUtil.getMinorVersion(21, 2)) {
                attributeInst = Registry.ATTRIBUTE.get(CommonUtil.parseNamespacedKey(attribute));
            } else {
                attributeInst = Enums.getIfPresent(Attribute.class,
                        attribute.toUpperCase(Locale.ENGLISH)).orNull();
            }
            if (attributeInst == null) {
                continue;
            }
            attributes.add(attributeInst);
        }
        AttributeUtil.removeCustomAttributeModifiers(meta, singleChange.getItem(),
                (attribute, modifier) -> attributes.contains(attribute));
        return singleChange.setItemMeta(meta);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("remove-attributes").isEmpty();
    }
}

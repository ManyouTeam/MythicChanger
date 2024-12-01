package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import com.google.common.base.Enums;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.Locale;
import java.util.UUID;

public class AddAttributes extends AbstractChangesRule {

    public AddAttributes() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ItemMeta meta = singleChange.getItemMeta();
        ObjectSingleChange attributesKey = singleChange.getConfigurationSection("add-attributes");
        if (attributesKey != null) {
            for (String attribute : attributesKey.getKeys(false)) {
                Attribute attributeInst;
                if (CommonUtil.getMinorVersion(21, 2)) {
                    attributeInst = Registry.ATTRIBUTE.get(CommonUtil.parseNamespacedKey(attribute));
                } else {
                    attributeInst = Enums.getIfPresent(Attribute.class, attribute.toUpperCase(Locale.ENGLISH)).orNull();
                }
                if (attributeInst == null) {
                    continue;
                }
                ObjectSingleChange subSection = attributesKey.getConfigurationSection(attribute);
                if (subSection == null) {
                    continue;
                }

                String attribId = subSection.getString("id");
                UUID id = attribId != null ? UUID.fromString(attribId) : UUID.randomUUID();
                String attribName = subSection.getString("name");
                double attribAmount = subSection.getDouble("amount");

                Collection<AttributeModifier> tempVal2 = meta.getAttributeModifiers(attributeInst);
                if (tempVal2 != null) {
                    for (AttributeModifier tempVal1 : tempVal2) {
                        attribAmount = attribAmount + tempVal1.getAmount();
                        meta.removeAttributeModifier(attributeInst, tempVal1);
                    }
                }

                String attribOperation = subSection.getString("operation");

                if (CommonUtil.getMinorVersion(20, 5)) {
                    String attribSlot = subSection.getString("slot");

                    EquipmentSlotGroup slot = EquipmentSlotGroup.ANY;

                    if (attribSlot != null) {
                        EquipmentSlotGroup targetSlot = EquipmentSlotGroup.getByName(attribSlot);
                        if (targetSlot != null) {
                            slot = targetSlot;
                        }
                    }

                    if (attribName != null && attribOperation != null) {
                        AttributeModifier modifier;
                        if (CommonUtil.getMajorVersion(21)) {
                            modifier = new AttributeModifier(
                                    CommonUtil.parseNamespacedKey(attribName),
                                    attribAmount,
                                    Enums.getIfPresent(AttributeModifier.Operation.class, attribOperation)
                                            .or(AttributeModifier.Operation.ADD_NUMBER),
                                    slot);
                        } else {
                            modifier = new AttributeModifier(
                                    id,
                                    attribName,
                                    attribAmount,
                                    Enums.getIfPresent(AttributeModifier.Operation.class, attribOperation)
                                            .or(AttributeModifier.Operation.ADD_NUMBER),
                                    slot);
                        }
                        meta.addAttributeModifier(attributeInst, modifier);
                    }
                } else {
                    String attribSlot = subSection.getString("slot");

                    EquipmentSlot slot = attribSlot != null ? Enums.getIfPresent(EquipmentSlot.class, attribSlot).or(EquipmentSlot.HAND) : null;

                    if (attribName != null && attribOperation != null) {
                        AttributeModifier modifier = new AttributeModifier(
                                id,
                                attribName,
                                attribAmount,
                                Enums.getIfPresent(AttributeModifier.Operation.class, attribOperation)
                                        .or(AttributeModifier.Operation.ADD_NUMBER),
                                slot);

                        meta.addAttributeModifier(attributeInst, modifier);
                    }
                }
            }
        }
        return singleChange.setItemMeta(meta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("add-attributes", 5);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("add-attributes") == null;
    }
}

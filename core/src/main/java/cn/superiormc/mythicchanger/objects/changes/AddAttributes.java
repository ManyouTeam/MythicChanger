package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.AttributeUtil;
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

import java.util.ArrayList;
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
                String attribOperation = subSection.getString("operation");

                if (CommonUtil.getMinorVersion(20, 5)) {
                    String attribSlot = subSection.getString("slot");
                    EquipmentSlotGroup slot = AttributeUtil.getAutomaticEquipmentSlotGroup(singleChange.getItem());
                    if (attribSlot != null) {
                        EquipmentSlotGroup targetSlot = EquipmentSlotGroup.getByName(attribSlot);
                        slot = targetSlot != null ? targetSlot : EquipmentSlotGroup.ANY;
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
                        AttributeUtil.copyDefaultAttributeModifiers(meta, singleChange.getItem());
                        addOrMergeAttributeModifier(meta, attributeInst, modifier);
                    }
                } else {
                    String attribSlot = subSection.getString("slot");
                    EquipmentSlot slot = attribSlot != null
                            ? Enums.getIfPresent(EquipmentSlot.class, attribSlot).or(EquipmentSlot.HAND)
                            : AttributeUtil.getAutomaticEquipmentSlot(singleChange.getItem());
                    if (attribName != null && attribOperation != null) {
                        AttributeModifier modifier = new AttributeModifier(
                                id,
                                attribName,
                                attribAmount,
                                Enums.getIfPresent(AttributeModifier.Operation.class, attribOperation)
                                        .or(AttributeModifier.Operation.ADD_NUMBER),
                                slot);
                        AttributeUtil.copyDefaultAttributeModifiers(meta, singleChange.getItem());
                        addOrMergeAttributeModifier(meta, attributeInst, modifier);
                    }
                }
            }
        }
        return singleChange.setItemMeta(meta);
    }

    private void addOrMergeAttributeModifier(ItemMeta meta, Attribute attribute,
                                             AttributeModifier modifier) {
        double amount = modifier.getAmount();
        Collection<AttributeModifier> existingModifiers = meta.getAttributeModifiers(attribute);
        if (modifier.getOperation() == AttributeModifier.Operation.ADD_NUMBER
                && existingModifiers != null) {
            for (AttributeModifier existing : new ArrayList<>(existingModifiers)) {
                if (existing.getOperation() == AttributeModifier.Operation.ADD_NUMBER
                        && existing.getName().equals(modifier.getName())) {
                    amount += existing.getAmount();
                    meta.removeAttributeModifier(attribute, existing);
                }
            }
        }

        meta.addAttributeModifier(attribute,
                amount == modifier.getAmount() ? modifier : withAmount(modifier, amount));
    }

    private AttributeModifier withAmount(AttributeModifier modifier, double amount) {
        if (CommonUtil.getMajorVersion(21)) {
            return new AttributeModifier(modifier.getKey(), amount, modifier.getOperation(),
                    modifier.getSlotGroup());
        }
        if (CommonUtil.getMinorVersion(20, 5)) {
            return new AttributeModifier(modifier.getUniqueId(), modifier.getName(), amount,
                    modifier.getOperation(), modifier.getSlotGroup());
        }
        return new AttributeModifier(modifier.getUniqueId(), modifier.getName(), amount,
                modifier.getOperation(), modifier.getSlot());
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("add-attributes") == null;
    }
}

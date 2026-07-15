package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.AttributeUtil;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.MathUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import com.google.common.base.Enums;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class SetAttribute extends AbstractChangesRule {

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ObjectSingleChange attributeSection = singleChange.getConfigurationSection("set-attribute");
        if (attributeSection == null) {
            return singleChange.getItem();
        }

        Map<Attribute, ConfiguredAttribute> configuredAttributes = new HashMap<>();
        for (String attributeId : attributeSection.getKeys(false)) {
            Attribute attribute;
            if (CommonUtil.getMinorVersion(21, 2)) {
                attribute = Registry.ATTRIBUTE.get(CommonUtil.parseNamespacedKey(attributeId));
            } else {
                attribute = Enums.getIfPresent(Attribute.class, attributeId.toUpperCase(Locale.ENGLISH)).orNull();
            }
            NamespacedKey modifierKey = CommonUtil.parseNamespacedKey(attributeId);
            if (attribute == null || modifierKey == null) {
                continue;
            }
            configuredAttributes.put(attribute,
                    new ConfiguredAttribute(modifierKey,
                            getConfiguredAmount(attributeSection, attributeId)));
        }

        ItemStack item = singleChange.getItem();
        ItemMeta meta = singleChange.getItemMeta();
        EquipmentSlot slot = AttributeUtil.getAutomaticEquipmentSlot(item);
        Multimap<Attribute, AttributeModifier> defaultModifiers =
                item.getType().getDefaultAttributeModifiers(slot);
        Multimap<Attribute, AttributeModifier> result =
                MultimapBuilder.hashKeys().hashSetValues().build();

        for (Map.Entry<Attribute, AttributeModifier> entry : defaultModifiers.entries()) {
            ConfiguredAttribute configured = configuredAttributes.get(entry.getKey());
            AttributeModifier modifier = entry.getValue();
            result.put(entry.getKey(), configured == null
                    ? modifier : withAmount(modifier, configured.amount));
        }

        for (Map.Entry<Attribute, ConfiguredAttribute> entry : configuredAttributes.entrySet()) {
            if (!defaultModifiers.containsKey(entry.getKey())) {
                result.put(entry.getKey(), createModifier(item, entry.getValue()));
            }
        }

        // Setting the complete map clears every previous explicit modifier while retaining
        // the current material's vanilla modifiers that were rebuilt above.
        meta.setAttributeModifiers(result);
        return singleChange.setItemMeta(meta);
    }

    private double getConfiguredAmount(ObjectSingleChange section, String attributeId) {
        // Minecraft attribute IDs before 1.21.2 contain dots (for example,
        // minecraft:generic.armor), which Bukkit otherwise treats as path separators.
        Object value = section.section.getValues(false).get(attributeId);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        if (value == null) {
            return 0;
        }
        return MathUtil.doCalculate(TextUtil.withPAPI(
                section.parsePlaceholder(value.toString()), section.getPlayer())).doubleValue();
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

    private AttributeModifier createModifier(ItemStack item, ConfiguredAttribute configured) {
        if (CommonUtil.getMajorVersion(21)) {
            return new AttributeModifier(configured.key, configured.amount,
                    AttributeModifier.Operation.ADD_NUMBER,
                    AttributeUtil.getAutomaticEquipmentSlotGroup(item));
        }

        UUID uuid = UUID.nameUUIDFromBytes(configured.key.toString()
                .getBytes(StandardCharsets.UTF_8));
        if (CommonUtil.getMinorVersion(20, 5)) {
            return new AttributeModifier(uuid, configured.key.toString(), configured.amount,
                    AttributeModifier.Operation.ADD_NUMBER,
                    AttributeUtil.getAutomaticEquipmentSlotGroup(item));
        }
        return new AttributeModifier(uuid, configured.key.toString(), configured.amount,
                AttributeModifier.Operation.ADD_NUMBER,
                AttributeUtil.getAutomaticEquipmentSlot(item));
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("set-attribute") == null;
    }

    private static class ConfiguredAttribute {

        private final NamespacedKey key;

        private final double amount;

        private ConfiguredAttribute(NamespacedKey key, double amount) {
            this.key = key;
            this.amount = amount;
        }
    }
}

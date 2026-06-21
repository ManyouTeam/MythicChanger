package cn.superiormc.mythicchanger.utils;

import cn.superiormc.enchantmentslots.EnchantmentSlots;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SlotUtil {

    public static final NamespacedKey ENCHANTMENT_SLOTS_KEY = new NamespacedKey(EnchantmentSlots.instance, "enchantment_slots");

    public static ItemStack setSlot(ObjectSingleChange singleChange, int slotValue, boolean override) {
        ItemMeta meta = singleChange.getItemMeta();
        if (meta == null) {
            return singleChange.getItem();
        }
        if (override) {
            meta.getPersistentDataContainer().remove(ENCHANTMENT_SLOTS_KEY);
        }
        if (meta.getPersistentDataContainer().has(ENCHANTMENT_SLOTS_KEY, PersistentDataType.INTEGER)) {
            return singleChange.getItem();
        }
        meta.getPersistentDataContainer().set(ENCHANTMENT_SLOTS_KEY,
                PersistentDataType.INTEGER,
                slotValue);
        return singleChange.setItemMeta(meta);
    }

    public static int getSlot(ObjectSingleChange singleChange) {
        ItemMeta meta = singleChange.getItemMeta();
        if (meta == null) {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(ENCHANTMENT_SLOTS_KEY, PersistentDataType.INTEGER)) {
            return 0;
        }
        return meta.getPersistentDataContainer().get(ENCHANTMENT_SLOTS_KEY, PersistentDataType.INTEGER);
    }

}

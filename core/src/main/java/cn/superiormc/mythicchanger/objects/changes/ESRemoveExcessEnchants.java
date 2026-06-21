package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.utils.SlotUtil;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ESRemoveExcessEnchants extends AbstractChangesRule {

    public ESRemoveExcessEnchants() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (singleChange.getBoolean("es-remove-excess-enchants")) {
            int maxEnchantments = SlotUtil.getSlot(singleChange);
            ItemStack targetItem = singleChange.getItem();
            ItemMeta meta = singleChange.getItemMeta();
            if (maxEnchantments > 0 && targetItem.getEnchantments().size() > maxEnchantments) {
                int removeAmount = targetItem.getEnchantments().size() - maxEnchantments;
                for (Enchantment enchant : targetItem.getEnchantments().keySet()) {
                    if (removeAmount <= 0) {
                        break;
                    }

                    if (meta == null) {
                        break;
                    }
                    meta.removeEnchant(enchant);
                    removeAmount--;
                }
            }
            return singleChange.setItemMeta(meta);
        }
        return singleChange.getItem();
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.getBoolean("es-remove-excess-enchants", false);
    }
}

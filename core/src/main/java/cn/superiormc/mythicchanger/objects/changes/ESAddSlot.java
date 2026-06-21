package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.utils.SlotUtil;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ESAddSlot extends AbstractChangesRule {

    public ESAddSlot() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        String amount = singleChange.getString("es-add-slot", "3");
        SlotUtil.setSlot(singleChange, Integer.parseInt(amount) + cn.superiormc.enchantmentslots.methods.SlotUtil.getSlot(singleChange.getItemMeta()), true);
        return singleChange.getItem();
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("es-add-slot");
    }

}

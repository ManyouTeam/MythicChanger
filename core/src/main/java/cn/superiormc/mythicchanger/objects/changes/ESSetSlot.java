package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.enchantmentslots.managers.ConfigManager;
import cn.superiormc.mythicchanger.utils.SlotUtil;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ESSetSlot extends AbstractChangesRule {

    public ESSetSlot() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        String amount = singleChange.getString("es-set-slot", "3");
        if (amount.equals("true")) {
            int defaultSlot = ConfigManager.configManager.getDefaultLimits(singleChange.getItem(), singleChange.getPlayer());
            return SlotUtil.setSlot(singleChange, defaultSlot, false);
        } else {
            SlotUtil.setSlot(singleChange, Integer.parseInt(amount), false);
            return singleChange.getItem();
        }
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("es-set-slot");
    }

}

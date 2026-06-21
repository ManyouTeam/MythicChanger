package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.enchantmentslots.methods.AddLore;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ESRemoveLore extends AbstractChangesRule {

    public ESRemoveLore() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        return AddLore.removeLore(singleChange.getItem(), singleChange.getPlayer());
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.getBoolean("es-remove-lore", false);
    }
}

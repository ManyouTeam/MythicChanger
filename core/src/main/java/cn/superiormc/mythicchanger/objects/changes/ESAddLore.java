package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.enchantmentslots.methods.AddLore;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ESAddLore extends AbstractChangesRule {

    public ESAddLore() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        return AddLore.addLore(singleChange.getItem(), singleChange.getPlayer());
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.getBoolean("es-add-lore", false);
    }
}

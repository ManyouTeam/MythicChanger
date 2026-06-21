package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.enchantmentslots.methods.AddLore;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ESParseLore extends AbstractChangesRule {

    public ESParseLore() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        return AddLore.parseLore(singleChange.getItem(), singleChange.getPlayer());
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.getBoolean("es-parse-lore", false);
    }
}

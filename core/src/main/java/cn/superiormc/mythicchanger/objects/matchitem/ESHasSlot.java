package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.enchantmentslots.methods.SlotUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ESHasSlot extends AbstractMatchItemRule {

    public ESHasSlot() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, Player player, ItemStack item, ItemMeta meta) {
        return SlotUtil.getSlot(item) >= section.getInt("has-slot");
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("has-slot");
    }
}

package cn.superiormc.mythicchanger.paper.methods;

import cn.superiormc.mythicchanger.paper.adapts.PaperComponentAdapter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.inventory.ItemStack;

public class DebuildItemPaper {

    public static ConfigurationSection serializeItemStack(ItemStack item) {
        return PaperComponentAdapter.serializeItemStack(item, new MemoryConfiguration());
    }

}

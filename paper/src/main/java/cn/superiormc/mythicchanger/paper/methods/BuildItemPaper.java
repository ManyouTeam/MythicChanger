package cn.superiormc.mythicchanger.paper.methods;

import cn.superiormc.mythicchanger.methods.BuildItem;
import cn.superiormc.mythicchanger.paper.adapts.PaperComponentAdapter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BuildItemPaper {

    public static ItemStack buildItemStack(Player player,
                                           ConfigurationSection section,
                                           int amount,
                                           String... args) {
        ItemStack item = BuildItem.buildItemStack(player, section, args);
        if (amount > 0) {
            item.setAmount(amount);
        }
        return item;
    }

    public static ItemStack editItemStack(ItemStack item,
                                          Player player,
                                          ConfigurationSection section,
                                          int amount,
                                          String... args) {
        if (amount > 0) {
            item.setAmount(amount);
        }
        return PaperComponentAdapter.editItemStack(item, player, section, args);
    }

}

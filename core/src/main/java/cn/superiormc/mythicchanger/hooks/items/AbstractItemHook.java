package cn.superiormc.mythicchanger.hooks.items;

import cn.superiormc.mythicchanger.manager.ErrorManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractItemHook {

    protected String pluginName;

    public AbstractItemHook(String pluginName) {
        this.pluginName = pluginName;
    }

    public abstract ItemStack getHookItemByID(Player player, String itemID);

    public ItemStack returnNullItem(String itemID) {
        ErrorManager.errorManager.sendErrorMessage("§cError: Can not get "
                + pluginName + " item: " + itemID + "!");
        return null;
    }

    public abstract String getIDByItemStack(ItemStack hookItem);

    public String getSimplyIDByItemStack(ItemStack hookItem, boolean useTier) {
        return getIDByItemStack(hookItem);
    }

    public String getPluginName() {
        return pluginName;
    }
}

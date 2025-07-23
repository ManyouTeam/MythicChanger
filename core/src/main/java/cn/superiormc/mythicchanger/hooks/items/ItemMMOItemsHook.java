package cn.superiormc.mythicchanger.hooks.items;

import cn.superiormc.mythicchanger.hooks.MMOItemsHook;
import cn.superiormc.mythicchanger.manager.ErrorManager;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.ItemTier;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemMMOItemsHook extends AbstractItemHook {

    public ItemMMOItemsHook() {
        super("MMOItems");
    }

    @Override
    public ItemStack getHookItemByID(Player player, String hookItemID) {
        Type type = MMOItems.plugin.getTypes().get(hookItemID.split(";;")[0]);
        if (type == null) {
            ErrorManager.errorManager.sendErrorMessage("§cError: Can not get "
                    + pluginName + " item: " + hookItemID + "!");
            return null;
        } else {
            MMOItemTemplate template = MMOItems.plugin.getTemplates().getTemplate(type, hookItemID.split(";;")[1]);
            if (template == null) {
                ErrorManager.errorManager.sendErrorMessage("§cError: Can not get "
                        + pluginName + " item: " + hookItemID + "!");
                return null;
            }
            MMOItemsHook.generateNewCache(template);
            return MMOItemsHook.getItem(template);
        }
    }

    @Override
    public String getIDByItemStack(ItemStack hookItem) {
        String tempVal1 = MMOItems.getID(hookItem);
        if (tempVal1 == null || tempVal1.isEmpty()) {
            return null;
        }
        String tempVal2 = MMOItems.getTypeName(hookItem);
        if (tempVal2 == null || tempVal2.isEmpty()) {
            return null;
        }
        else {
            return tempVal2 + ";;" + tempVal1;
        }
    }

    @Override
    public String getSimplyIDByItemStack(ItemStack hookItem, boolean useTier) {
        String tempVal1 = MMOItems.getID(hookItem);
        if (tempVal1 != null && !tempVal1.isEmpty()) {
            if (useTier) {
                ItemTier tempVal2 = ItemTier.ofItem(NBTItem.get(hookItem));
                if (tempVal2 != null) {
                    return tempVal2.getId();
                }
            }
            return tempVal1;
        }
        return null;
    }
}

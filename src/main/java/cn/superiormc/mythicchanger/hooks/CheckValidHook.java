package cn.superiormc.mythicchanger.hooks;

import cn.superiormc.mythicchanger.utils.CommonUtil;
import com.willfp.eco.core.items.Items;
import com.willfp.ecoarmor.sets.ArmorSet;
import com.willfp.ecoarmor.sets.ArmorUtils;
import com.willfp.ecoitems.items.EcoItem;
import com.willfp.ecoitems.items.ItemUtilsKt;
import dev.lone.itemsadder.api.CustomStack;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.lib.api.item.NBTItem;
import io.th0rgal.oraxen.api.OraxenItems;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.ItemTier;
import org.bukkit.inventory.ItemStack;
import pers.neige.neigeitems.manager.ItemManager;

public class CheckValidHook {

    public static String checkValid(ItemStack itemStack, boolean useTier) {
        if (CommonUtil.checkPluginLoad("ItemsAdder")) {
            CustomStack customStack = CustomStack.byItemStack(itemStack);
            if (customStack != null) {
                return customStack.getId();
            }
        }
        if (CommonUtil.checkPluginLoad("Oraxen")) {
            String tempVal1 = OraxenItems.getIdByItem(itemStack);
            if (tempVal1 != null) {
                return tempVal1;
            }
        }
        if (CommonUtil.checkPluginLoad("MMOItems")) {
            String tempVal1 = MMOItems.getID(itemStack);
            if (tempVal1 != null && !tempVal1.isEmpty()) {
                if (useTier) {
                    ItemTier tempVal2 = ItemTier.ofItem(NBTItem.get(itemStack));
                    if (tempVal2 != null) {
                        return tempVal2.getId();
                    }
                }
                return tempVal1;
            }
        }
        if (CommonUtil.checkPluginLoad("EcoItems")) {
            EcoItem tempVal1 = ItemUtilsKt.getEcoItem(itemStack);
            if (tempVal1 != null) {
                return tempVal1.getID();
            }
        }
        if (CommonUtil.checkPluginLoad("EcoArmor")) {
            ArmorSet tempVal1 = ArmorUtils.getSetOnItem(itemStack);
            if (tempVal1 != null) {
                return tempVal1.getId();
            }
        }
        if (CommonUtil.checkPluginLoad("eco")) {
            if (Items.getCustomItem(itemStack) != null) {
                return Items.getCustomItem(itemStack).getKey().getKey();
            }
        }
        if (CommonUtil.checkPluginLoad("MythicMobs")) {
            String tempVal1 = MythicBukkit.inst().getItemManager().getMythicTypeFromItem(itemStack);
            if (tempVal1 != null) {
                return tempVal1;
            }
        }
        if (CommonUtil.checkPluginLoad("NeigeItems")) {
            if (ItemManager.INSTANCE.isNiItem(itemStack) != null) {
                return ItemManager.INSTANCE.isNiItem(itemStack).getId();
            }
        }
        return itemStack.getType().getKey().getKey().toLowerCase();
    }
}

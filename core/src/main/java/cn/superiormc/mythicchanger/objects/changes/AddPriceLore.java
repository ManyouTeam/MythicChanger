package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.ultimateshop.api.ShopHelper;
import cn.superiormc.ultimateshop.objects.buttons.ObjectItem;
import cn.superiormc.ultimateshop.utils.CommonUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AddPriceLore extends AbstractChangesRule {

    public AddPriceLore() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ItemStack[] items = new ItemStack[]{singleChange.getItem()};
        Inventory inventory = Bukkit.createInventory(singleChange.getPlayer(), InventoryType.CHEST);
        inventory.setStorageContents(items);

        ObjectItem objectItem = ShopHelper.getTargetItem(items, singleChange.getPlayer());
        if (objectItem == null) {
            return singleChange.getItem();
        }

        String singleSellPrice = ShopHelper.getSellPricesDisplay(items, singleChange.getPlayer(), 1);
        String singleBuyPrice = ShopHelper.getBuyPricesDisplay(items, singleChange.getPlayer(), 1);

        String totalSellPrice = ShopHelper.getSellPricesDisplay(items,
                singleChange.getPlayer(),
                objectItem.getReward().getMaxAbleSellAmount(inventory,
                        singleChange.getPlayer(),
                        ShopHelper.getSellUseTimes(objectItem, singleChange.getPlayer()),
                        1,
                        singleChange.getItem().getMaxStackSize()).getMaxAmount());

        ItemMeta meta = singleChange.getItemMeta();
        List<String> itemLore = singleChange.getStringList("add-price-lore");
        if (meta.hasLore()) {
            itemLore.addAll(MythicChanger.methodUtil.getItemLore(meta));
        }

        List<String> modifiedLore = new ArrayList<>();

        for (String singleLore : itemLore) {
            if (singleLore.contains("{buy-price}") && (singleBuyPrice == null || singleBuyPrice.isEmpty())) {
                continue;
            }
            if (singleLore.contains("{sell-price}") && (singleSellPrice == null || singleSellPrice.isEmpty())) {
                continue;
            }
            if (singleLore.contains("{total-price}") && (singleSellPrice == null || singleSellPrice.isEmpty())) {
                continue;
            }
            modifiedLore.add(singleLore);
        }

        modifiedLore = CommonUtil.modifyList(singleChange.getPlayer(), modifiedLore,
                "price", singleSellPrice,
                "buy-price", singleBuyPrice,
                "sell-price", singleSellPrice,
                "total-price", totalSellPrice);

        MythicChanger.methodUtil.setItemLore(meta, modifiedLore, singleChange.getPlayer());
        return singleChange.setItemMeta(meta);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("add-price-lore");
    }
}

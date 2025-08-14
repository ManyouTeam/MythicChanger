package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.ultimateshop.managers.CacheManager;
import cn.superiormc.ultimateshop.managers.ConfigManager;
import cn.superiormc.ultimateshop.objects.buttons.ObjectItem;
import cn.superiormc.ultimateshop.objects.caches.ObjectUseTimesCache;
import cn.superiormc.ultimateshop.objects.items.GiveResult;
import cn.superiormc.ultimateshop.objects.items.prices.ObjectPrices;
import cn.superiormc.ultimateshop.utils.CommonUtil;
import cn.superiormc.ultimateshop.utils.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AddPriceLore extends AbstractChangesRule {

    public AddPriceLore() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        String price = getPrice(singleChange);
        if (price == null) {
            return singleChange.getItem();
        }

        ItemMeta meta = singleChange.getItemMeta();
        List<String> itemLore = singleChange.getStringList("add-price-lore");
        if (meta.hasLore()) {
            itemLore.addAll(MythicChanger.methodUtil.getItemLore(meta));
        }

        itemLore = CommonUtil.modifyList(singleChange.getPlayer(), itemLore, "price", price);

        MythicChanger.methodUtil.setItemLore(meta, itemLore, singleChange.getPlayer());
        return singleChange.setItemMeta(meta);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("add-price-lore");
    }

    private String getPrice(ObjectSingleChange singleChange) {
        for (String shop : ConfigManager.configManager.shopConfigs.keySet()) {
            for (ObjectItem products : ConfigManager.configManager.getShop(shop).getProductList()) {
                if (ItemUtil.isSameItem(singleChange.getItem(), products.getDisplayItem(singleChange.getPlayer()))) {
                    ObjectUseTimesCache cache = CacheManager.cacheManager.getPlayerCache(singleChange.getPlayer()).getUseTimesCache().get(products);
                    if (cache == null) {
                        CacheManager.cacheManager.getPlayerCache(singleChange.getPlayer()).createUseTimesCache(products);
                    }
                    if (cache != null) {
                        GiveResult giveResult = products.getSellPrice().giveSingleThing(singleChange.getPlayer(), cache.getSellUseTimes(), 1);
                        return ObjectPrices.getDisplayNameInLine(singleChange.getPlayer(),
                                1,
                                giveResult.getResultMap(),
                                products.getSellPrice().getMode(),
                                !ConfigManager.configManager.getBoolean("placeholder.status.can-used-everywhere"));
                    }

                }
            }
        }
        return null;
    }
}

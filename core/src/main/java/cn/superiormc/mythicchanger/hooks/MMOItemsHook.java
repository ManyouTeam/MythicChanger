package cn.superiormc.mythicchanger.hooks;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.utils.SchedulerUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MMOItemsHook {

    private static Map<MMOItemTemplate, ItemStack> itemCaches = new ConcurrentHashMap<>();

    //private static boolean using;

    public static void generateNewCache() {
        MythicChanger.methodUtil.sendMessage(null, TextUtil.pluginPrefix() + " §fRegistering special item register manager" +
                " for MMOItems because it does not support async...");
        itemCaches = new ConcurrentHashMap<>();
        for (Type type : MMOItems.plugin.getTypes().getAll()) {
            for (MMOItemTemplate template : MMOItems.plugin.getTemplates().getTemplates(type)) {
                itemCaches.put(template, template.newBuilder().build().newBuilder().build());
            }
        }
    }

    public static void generateNewCache(MMOItemTemplate template) {
        //if (using) {
        //    return;
        //}
        SchedulerUtil.runSync(() -> {
            itemCaches.put(template, template.newBuilder().build().newBuilder().build());
        });
    }

    public static ItemStack getItem(MMOItemTemplate template) {
        //using = true;
        ItemStack resultItem = itemCaches.get(template);
        //using = false;
        if (resultItem == null || resultItem.getType() == Material.STONE) {
            return null;
        }
        return resultItem;
    }

}

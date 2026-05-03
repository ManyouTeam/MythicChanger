package cn.superiormc.mythicchanger.manager;

import cn.gtemc.itembridge.api.ItemBridge;
import cn.gtemc.itembridge.api.util.Pair;
import cn.gtemc.itembridge.core.BukkitItemBridge;
import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.hooks.MMOItemsHook;
import cn.superiormc.mythicchanger.hooks.items.*;
import cn.superiormc.mythicchanger.listeners.QuickShopListener;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import com.loohp.interactivechat.api.InteractiveChatAPI;
import com.loohp.interactivechat.objectholders.ICPlayer;
import com.loohp.interactivechat.objectholders.ICPlayerFactory;
import me.arasple.mc.trchat.module.internal.hook.HookPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HookManager {

    public static HookManager hookManager;

    private Map<String, AbstractItemHook> itemHooks;

    private ItemBridge<ItemStack, Player> itemBridgeHook = null;

    public HookManager() {
        hookManager = this;
        initNormalHook();
        if (ConfigManager.configManager.getString("hook-item-method").equalsIgnoreCase("DEFAULT")) {
            initItemHook();
        } else {
            itemBridgeHook = BukkitItemBridge.builder()
                    .onHookSuccess(p -> cn.superiormc.ultimateshop.utils.TextUtil.sendMessage(null, cn.superiormc.ultimateshop.utils.TextUtil.pluginPrefix() + " §fUSItemBridge successfully hook into " + p + "."))
                    .detectSupportedPlugins()
                    .build();
        }
    }

    private void initNormalHook() {
        if (CommonUtil.checkPluginLoad("InteractiveChat")) {
            TextUtil.sendMessage(null, TextUtil.pluginPrefix() + " §fHooking into InteractiveChat...");
            InteractiveChatAPI.registerItemStackTransformProvider(MythicChanger.instance, 10, (itemStack, uuid) -> {
                ICPlayer icPlayer = ICPlayerFactory.getICPlayer(uuid);
                return ConfigManager.configManager.startFakeChange(itemStack, icPlayer.getLocalPlayer(), true);
            });
        }
        if (CommonUtil.checkPluginLoad("TrChat")) {
            TextUtil.sendMessage(null, TextUtil.pluginPrefix() + " §fHooking into TrChat...");
            HookPlugin.INSTANCE.registerDisplayItemHook("EnchantmentSlots", (itemStack, player) -> ConfigManager.configManager.startFakeChange(itemStack, player, true));
        }
        if (CommonUtil.checkPluginLoad("QuickShop-Hikari")) {
            TextUtil.sendMessage(null, TextUtil.pluginPrefix() + " §fHooking into QuickShop-Hikari...");
            Bukkit.getPluginManager().registerEvents(new QuickShopListener(), MythicChanger.instance);
        }
        if (CommonUtil.checkPluginLoad("MMOItems")) {
            try {
                MMOItemsHook.generateNewCache();
            } catch (Throwable throwable) {
                ErrorManager.errorManager.sendErrorMessage("§cError: Failed to register MMOItems hook, consider update " +
                        "your MMOItems to latest dev version!");
            }
        }
    }

    private void initItemHook() {
        itemHooks = new HashMap<>();
        if (CommonUtil.checkPluginLoad("ItemsAdder")) {
            registerNewItemHook("ItemsAdder", new ItemItemsAdderHook());
        }
        if (CommonUtil.checkPluginLoad("Oraxen")) {
            registerNewItemHook("Oraxen", new ItemOraxenHook());
        }
        if (CommonUtil.checkPluginLoad("MMOItems")) {
            registerNewItemHook("MMOItems", new ItemMMOItemsHook());
        }
        if (CommonUtil.checkPluginLoad("EcoItems")) {
            registerNewItemHook("EcoItems", new ItemEcoItemsHook());
        }
        if (CommonUtil.checkPluginLoad("EcoArmor")) {
            registerNewItemHook("EcoArmor", new ItemEcoArmorHook());
        }
        if (CommonUtil.checkPluginLoad("MythicMobs")) {
            registerNewItemHook("MythicMobs", new ItemMythicMobsHook());
        }
        if (CommonUtil.checkPluginLoad("eco")) {
            registerNewItemHook("eco", new ItemecoHook());
        }
        if (CommonUtil.checkPluginLoad("NeigeItems")) {
            registerNewItemHook("NeigeItems", new ItemNeigeItemsHook());
        }
        if (CommonUtil.checkPluginLoad("ExecutableItems")) {
            registerNewItemHook("ExecutableItems", new ItemExecutableItemsHook());
        }
        if (CommonUtil.checkPluginLoad("Nexo")) {
            registerNewItemHook("Nexo", new ItemNexoHook());
        }
        if (CommonUtil.checkPluginLoad("CraftEngine")) {
            registerNewItemHook("CraftEngine", new ItemCraftEngineHook());
        }
    }

    public void registerNewItemHook(String pluginName,
                                    AbstractItemHook itemHook) {
        if (!itemHooks.containsKey(pluginName)) {
            TextUtil.sendMessage(null, TextUtil.pluginPrefix() + " §fHooking into " + pluginName + "...");
            itemHooks.put(pluginName, itemHook);
        }
    }

    public String parseItemID(ItemStack hookItem, boolean useTier) {
        if (!hookItem.hasItemMeta()) {
            return hookItem.getType().name().toLowerCase();
        }
        if (itemBridgeHook != null) {
            Pair<String, String> tempVal1 = itemBridgeHook.getFirstId(hookItem);
            if (tempVal1 != null) {
                return tempVal1.right;
            }
        }
        for (AbstractItemHook itemHook : itemHooks.values()) {
            String tempVal1 = itemHook.getSimplyIDByItemStack(hookItem, useTier);
            if (tempVal1 != null) {
                return tempVal1;
            }
        }
        return hookItem.getType().name().toLowerCase();
    }

    public String[] getHookItemPluginAndID(ItemStack hookItem) {
        if (itemBridgeHook != null) {
            Pair<String, String> tempVal1 = itemBridgeHook.getFirstId(hookItem);
            if (tempVal1 != null) {
                return new String[]{tempVal1.left, tempVal1.right};
            }
        }
        for (AbstractItemHook itemHook : itemHooks.values()) {
            String itemID = itemHook.getIDByItemStack(hookItem);
            if (itemID != null) {
                return new String[]{itemHook.getPluginName(), itemHook.getIDByItemStack(hookItem)};
            }
        }
        return null;
    }

    public ItemStack getHookItem(Player player, String pluginName, String itemID) {
        if (itemBridgeHook != null) {
            Optional<ItemStack> tempVal1 = itemBridgeHook.build(pluginName, player, itemID);
            if (tempVal1.isPresent()) {
                return tempVal1.get();
            }
        }
        if (!itemHooks.containsKey(pluginName)) {
            ErrorManager.errorManager.sendErrorMessage("§cError: Can not hook into "
                    + pluginName + " plugin, maybe we do not support this plugin, or your server didn't correctly load " +
                    "this plugin!");
            return null;
        }
        AbstractItemHook itemHook = itemHooks.get(pluginName);
        return itemHook.getHookItemByID(player, itemID);
    }
}

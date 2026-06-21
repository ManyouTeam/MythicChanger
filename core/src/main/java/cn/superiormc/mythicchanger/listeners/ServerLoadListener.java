package cn.superiormc.mythicchanger.listeners;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ChangesManager;
import cn.superiormc.mythicchanger.manager.MatchItemManager;
import cn.superiormc.mythicchanger.objects.changes.*;
import cn.superiormc.mythicchanger.objects.matchitem.ESHasSlot;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class ServerLoadListener implements Listener {

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        if (event.getType() == ServerLoadEvent.LoadType.STARTUP) {
            if (CommonUtil.getClass("cn.superiormc.ultimateshop.UltimateShop")) {
                MythicChanger.methodUtil.sendChat(null, TextUtil.pluginPrefix() + " §fHooking into UltimateShop...");
                ChangesManager.changesManager.registerNewRule(new AddPriceLore());
            }
            if (CommonUtil.getClass("cn.superiormc.enchantmentslots.EnchantmentSlots")) {
                MythicChanger.methodUtil.sendChat(null, TextUtil.pluginPrefix() + " §fHooking into EnchantmentSlots...");
                ChangesManager.changesManager.registerNewRule(new ESAddLore());
                ChangesManager.changesManager.registerNewRule(new ESAddSlot());
                ChangesManager.changesManager.registerNewRule(new ESSetSlot());
                ChangesManager.changesManager.registerNewRule(new ESResetSlot());
                ChangesManager.changesManager.registerNewRule(new ESRemoveLore());
                ChangesManager.changesManager.registerNewRule(new ESParseLore());
                ChangesManager.changesManager.registerNewRule(new ESRemoveExcessEnchants());
                MatchItemManager.matchItemManager.registerNewRule(new ESHasSlot());
            }
        }
    }
}

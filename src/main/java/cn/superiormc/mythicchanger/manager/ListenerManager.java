package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.hooks.MMOItemsReloadListener;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.Bukkit;

public class ListenerManager {

    public static ListenerManager listenerManager;

    public ListenerManager(){
        listenerManager = this;
        registerListeners();
    }

    private void registerListeners(){
        if (CommonUtil.checkPluginLoad("MMOItems")) {
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fRegistering special item register manager" +
                    " for MMOItems because it does not support async...");
            Bukkit.getPluginManager().registerEvents(new MMOItemsReloadListener(), MythicChanger.instance);
        }
    }
}

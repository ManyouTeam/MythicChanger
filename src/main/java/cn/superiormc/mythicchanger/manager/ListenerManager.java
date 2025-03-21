package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.listeners.MMOItemsReloadListener;
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
            Bukkit.getPluginManager().registerEvents(new MMOItemsReloadListener(), MythicChanger.instance);
        }
    }
}

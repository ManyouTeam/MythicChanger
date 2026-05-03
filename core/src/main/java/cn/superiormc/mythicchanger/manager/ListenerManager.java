package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.listeners.*;
import cn.superiormc.mythicchanger.protolcol.pacetevents.*;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import com.github.retrooper.packetevents.PacketEvents;
import org.bukkit.Bukkit;

public class ListenerManager {

    public static ListenerManager listenerManager;

    public ListenerManager(){
        listenerManager = this;
        initPacketEvents();
        registerListeners();
    }

    private void initPacketEvents() {
        if (ConfigManager.configManager.getBoolean("packet-listener", true)) {
            PacketEvents.getAPI().getEventManager().registerListener(new SetSlots(), ConfigManager.configManager.getPriority());
            PacketEvents.getAPI().getEventManager().registerListener(new WindowItem(), ConfigManager.configManager.getPriority());
            PacketEvents.getAPI().getEventManager().registerListener(new WindowMerchant(), ConfigManager.configManager.getPriority());
            if (CommonUtil.getMinorVersion(21, 5)) {
                PacketEvents.getAPI().getEventManager().registerListener(new SetCursorItem(), ConfigManager.configManager.getPriority());
                PacketEvents.getAPI().getEventManager().registerListener(new ContainerClick(), ConfigManager.configManager.getPriority());
            }
            new WindowClick();
        }
    }

    private void registerListeners() {
        if (CommonUtil.checkPluginLoad("MMOItems")) {
            Bukkit.getPluginManager().registerEvents(new MMOItemsReloadListener(), MythicChanger.instance);
        }
        Bukkit.getPluginManager().registerEvents(new PlayerCacheListener(), MythicChanger.instance);
        if (!ConfigManager.configManager.getString("apply-item-mode", "").equalsIgnoreCase("GUI") ||
                ConfigManager.configManager.getBoolean("apply-item-mode.drag-enabled", true)) {
            Bukkit.getPluginManager().registerEvents(new ApplyItemListener(), MythicChanger.instance);
        }
        if (CommonUtil.getMinorVersion(16, 5) && !ChangesManager.changesManager.containsLog("UltimateShop")) {
            Bukkit.getPluginManager().registerEvents(new ServerLoadListener(), MythicChanger.instance);
        }
        if (CommonUtil.getMajorVersion(19) && MythicChanger.methodUtil.methodID().equals("paper") &&
                ConfigManager.configManager.getBoolean("change-gui.anti-dupe-checker", false)) {
            Bukkit.getPluginManager().registerEvents(new DupeListener(), MythicChanger.instance);
        }
    }
}

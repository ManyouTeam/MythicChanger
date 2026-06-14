package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.listeners.*;
import cn.superiormc.mythicchanger.protolcol.pacetevents.*;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.gui.InvGUI;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerCommon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ListenerManager {

    public static ListenerManager listenerManager;

    private final Map<UUID, InvGUI> listeners = new HashMap<>();

    private final Collection<PacketListenerCommon> packetListeners = new ArrayList<>();

    public ListenerManager(){
        listenerManager = this;
        initPacketEvents();
        registerListeners();
    }

    private void initPacketEvents() {
        if (ConfigManager.configManager.getBoolean("packet-listener", true)) {
            registerPacketListener(new SetSlots());
            registerPacketListener(new WindowItem());
            registerPacketListener(new WindowMerchant());
            if (CommonUtil.getMinorVersion(21, 5)) {
                registerPacketListener(new SetCursorItem());
                registerPacketListener(new ContainerClick());
            }
            new WindowClick();
        }
    }

    private void registerPacketListener(PacketListener listener) {
        packetListeners.add(PacketEvents.getAPI().getEventManager().registerListener(
                listener, ConfigManager.configManager.getPriority()));
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new GUIListener(), MythicChanger.instance);
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

    public void registerNewGUIListener(Player player, InvGUI inv) {
        unregisterListeners(player);
        listeners.put(player.getUniqueId(), inv);
    }

    public void unregisterNewGUIListener(Player player, InvGUI inv) {
        listeners.remove(player.getUniqueId(), inv);
    }

    public void unregisterListeners(Player player) {
        listeners.remove(player.getUniqueId());
    }

    public InvGUI getInvGUI(Player player) {
        return listeners.get(player.getUniqueId());
    }

    public void unregisterAllListener() {
        HandlerList.unregisterAll(MythicChanger.instance);
        PacketEvents.getAPI().getEventManager().unregisterListeners(packetListeners.toArray(new PacketListenerCommon[0]));
        packetListeners.clear();
    }
}

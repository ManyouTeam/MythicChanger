package cn.superiormc.mythicchanger;

import cn.superiormc.mythicchanger.hooks.MMOItemsHook;
import cn.superiormc.mythicchanger.listeners.ApplyItemListener;
import cn.superiormc.mythicchanger.manager.*;
import cn.superiormc.mythicchanger.protolcol.ProtocolLib.SetSlots;
import cn.superiormc.mythicchanger.protolcol.ProtocolLib.WindowClick;
import cn.superiormc.mythicchanger.protolcol.ProtocolLib.WindowItem;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MythicChanger extends JavaPlugin {

    public static MythicChanger instance;

    public static boolean freeVersion = false;

    @Override
    public void onEnable() {
        instance = this;
        new InitManager();
        Bukkit.getScheduler().runTaskAsynchronously(MythicChanger.instance, () -> {
            new SetSlots();
            new WindowItem();
            new WindowClick();
        });
        new LanguageManager();
        new ErrorManager();
        new ConfigManager();
        new ItemManager();
        new ChangesManager();
        new MatchItemManager();
        new CommandManager();
        if (!MythicChanger.freeVersion) {
            Bukkit.getPluginManager().registerEvents(new ApplyItemListener(), this);
        }
        if (CommonUtil.checkPluginLoad("MMOItems")) {
            try {
                Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fRegistering special item register manager" +
                        " for MMOItems because it does not support async...");
                MMOItemsHook.generateNewCache();
                new ListenerManager();
            } catch (Exception ignored) {
                Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §cFalied to register MMOItems hook!");
            }
        }
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fPlugin is loaded. Author: PQguanfang.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fPlugin is disabled. Author: PQguanfang.");
    }
}

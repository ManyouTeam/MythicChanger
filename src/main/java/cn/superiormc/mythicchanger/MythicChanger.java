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

    public static boolean isPaper = false;

    public static boolean freeVersion = true;

    @Override
    public void onEnable() {
        instance = this;
        new ErrorManager();
        new InitManager();
        new ConfigManager();
        new LanguageManager();
        if (CommonUtil.getClass("com.destroystokyo.paper.PaperConfig")) {
            isPaper = true;
        }
        Bukkit.getScheduler().runTaskAsynchronously(MythicChanger.instance, () -> {
            new SetSlots();
            new WindowItem();
            new WindowClick();
        });
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
            } catch (Throwable throwable) {
                ErrorManager.errorManager.sendErrorMessage("§x§9§8§F§B§9§8[MythicChanger] §cError: Failed to register MMOItems hook, consider update " +
                        "your MMOItems to latest dev version!");
            }
        }
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fPlugin is loaded. Author: PQguanfang.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fPlugin is disabled. Author: PQguanfang.");
    }
}

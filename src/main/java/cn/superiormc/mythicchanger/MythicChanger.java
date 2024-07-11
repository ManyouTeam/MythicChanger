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

    public static boolean freeVersion = false;

    public static int majorVersion;

    public static int miniorVersion;

    @Override
    public void onEnable() {
        instance = this;
        try {
            String[] versionParts = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
            majorVersion = versionParts.length > 1 ? Integer.parseInt(versionParts[1]) : 0;
            miniorVersion = versionParts.length > 2 ? Integer.parseInt(versionParts[2]) : 0;
        } catch (Throwable throwable) {
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §cError: Can not get your Minecraft version! Default set to 1.0.0.");
        }
        new ErrorManager();
        new InitManager();
        new ConfigManager();
        new LanguageManager();
        if (CommonUtil.getClass("com.destroystokyo.paper.PaperConfig")) {
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fPaper is found, enabled Paper only feature!");
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
        if (LocateManager.enableThis()) {
            new LocateManager();
        }
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
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fYour Minecraft version is: 1." + majorVersion + "." + miniorVersion + "!");
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fPlugin is loaded. Author: PQguanfang.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fPlugin is disabled. Author: PQguanfang.");
    }
}

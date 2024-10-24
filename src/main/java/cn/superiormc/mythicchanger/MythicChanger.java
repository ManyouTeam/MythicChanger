package cn.superiormc.mythicchanger;

import cn.superiormc.mythicchanger.listeners.ApplyItemListener;
import cn.superiormc.mythicchanger.manager.*;
import cn.superiormc.mythicchanger.protolcol.ProtocolLib.*;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MythicChanger extends JavaPlugin {

    public static MythicChanger instance;

    public static boolean isPaper = false;

    public static final boolean freeVersion = true;

    public static int majorVersion;

    public static int miniorVersion;

    public static boolean newSkullMethod;

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
        new ActionManager();
        new ConditionManager();
        new ConfigManager();
        new HookManager();
        new LanguageManager();
        if (CommonUtil.getClass("com.destroystokyo.paper.PaperConfig")) {
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fPaper is found, enabled Paper only feature!");
            isPaper = true;
        }
        if (ConfigManager.configManager.getBoolean("pack-listener", true)) {
            Bukkit.getScheduler().runTaskAsynchronously(MythicChanger.instance, () -> {
                new SetSlots();
                new WindowItem();
                new WindowClick();
                new WindowMerchant();
            });
        }
        new ItemManager();
        new ChangesManager();
        new MatchItemManager();
        new CommandManager();
        if (LocateManager.enableThis()) {
            new LocateManager();
        }
        if (!MythicChanger.freeVersion && ConfigManager.configManager.getString("apply-item-mode", "DRAG").equalsIgnoreCase("DRAG")) {
            Bukkit.getPluginManager().registerEvents(new ApplyItemListener(), this);
        }
        if (!CommonUtil.checkClass("com.mojang.authlib.properties.Property", "getValue") && CommonUtil.getMinorVersion(21, 1)) {
            newSkullMethod = true;
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fNew AuthLib found, enabled new skull get method!");
        }
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fYour Minecraft version is: 1." + majorVersion + "." + miniorVersion + "!");
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fPlugin is loaded. Author: PQguanfang.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fPlugin is disabled. Author: PQguanfang.");
    }
}

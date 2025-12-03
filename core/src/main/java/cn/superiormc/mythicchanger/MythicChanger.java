package cn.superiormc.mythicchanger;

import cn.superiormc.mythicchanger.listeners.ApplyItemListener;
import cn.superiormc.mythicchanger.listeners.PlayerCacheListener;
import cn.superiormc.mythicchanger.listeners.ServerLoadListener;
import cn.superiormc.mythicchanger.manager.*;
import cn.superiormc.mythicchanger.protolcol.ProtocolLib.*;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.SpecialMethodUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import com.github.retrooper.packetevents.PacketEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MythicChanger extends JavaPlugin {

    public static MythicChanger instance;

    public static boolean isFolia = false;

    public static final boolean freeVersion = true;

    public static SpecialMethodUtil methodUtil;

    public static int majorVersion;

    public static int minorVersion;

    public static boolean newSkullMethod;

    @Override
    public void onEnable() {
        instance = this;
        try {
            String[] versionParts = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
            majorVersion = versionParts.length > 1 ? Integer.parseInt(versionParts[1]) : 0;
            minorVersion = versionParts.length > 2 ? Integer.parseInt(versionParts[2]) : 0;
        } catch (Throwable throwable) {
            Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §cError: Can not get your Minecraft version! Default set to 1.0.0.");
        }
        if (CommonUtil.getClass("com.destroystokyo.paper.PaperConfig") && CommonUtil.getMinorVersion(17, 1)) {
            try {
                Class<?> paperClass = Class.forName("cn.superiormc.mythicchanger.paper.PaperMethodUtil");
                methodUtil = (SpecialMethodUtil) paperClass.getDeclaredConstructor().newInstance();
                MythicChanger.methodUtil.sendMessage(null, TextUtil.pluginPrefix() + " §fPaper is found, entering Paper plugin mode...!");
            } catch (Throwable throwable) {
                Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §cError: The plugin seems break, please download it again from site.");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        } else {
            try {
                Class<?> spigotClass = Class.forName("cn.superiormc.mythicchanger.spigot.SpigotMethodUtil");
                methodUtil = (SpecialMethodUtil) spigotClass.getDeclaredConstructor().newInstance();
                MythicChanger.methodUtil.sendMessage(null, TextUtil.pluginPrefix() + " §fSpigot is found, entering Spigot plugin mode...!");
            } catch (Throwable throwable) {
                Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §cError: The plugin seems break, please download it again from site.");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        }
        if (CommonUtil.getClass("io.papermc.paper.threadedregions.RegionizedServer")) {
            Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fFolia is found, enabled Folia compatibility feature!");
            Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §6Warning: Folia support is not fully test, major bugs maybe found! " +
                    "Please do not use in production environment!");
            isFolia = true;
        }
        new ErrorManager();
        new InitManager();
        new ActionManager();
        new ConditionManager();
        new ConfigManager();
        new HookManager();
        new LanguageManager();
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
        new ItemManager();
        new ChangesManager();
        new MatchItemManager();
        new CommandManager();
        if (LocateManager.enableThis()) {
            new LocateManager();
        }
        Bukkit.getPluginManager().registerEvents(new PlayerCacheListener(), this);
        if (!ConfigManager.configManager.getString("apply-item-mode", "DRAG").equalsIgnoreCase("GUI") ||
        ConfigManager.configManager.getBoolean("apply-item-mode.drag-enabled", true)) {
            Bukkit.getPluginManager().registerEvents(new ApplyItemListener(), this);
        }
        if (!CommonUtil.checkClass("com.mojang.authlib.properties.Property", "getValue") && CommonUtil.getMinorVersion(21, 1)) {
            newSkullMethod = true;
            Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fNew AuthLib found, enabled new skull get method!");
        }
        if (CommonUtil.getMinorVersion(16, 5) && !ChangesManager.changesManager.containsLog("UltimateShop")) {
            Bukkit.getPluginManager().registerEvents(new ServerLoadListener(), this);
        }
        Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fYour Minecraft version is: 1." + majorVersion + "." + minorVersion + "!");
        Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fPlugin is loaded. Author: PQguanfang.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fPlugin is disabled. Author: PQguanfang.");
    }
}

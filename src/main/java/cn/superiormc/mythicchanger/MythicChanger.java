package cn.superiormc.mythicchanger;

import cn.superiormc.mythicchanger.manager.*;
import cn.superiormc.mythicchanger.protolcol.ProtocolLib.SetSlots;
import cn.superiormc.mythicchanger.protolcol.ProtocolLib.WindowClick;
import cn.superiormc.mythicchanger.protolcol.ProtocolLib.WindowItem;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MythicChanger extends JavaPlugin {

    public static MythicChanger instance;

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
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fPlugin is loaded. Author: PQguanfang.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fPlugin is disabled. Author: PQguanfang.");
    }
}

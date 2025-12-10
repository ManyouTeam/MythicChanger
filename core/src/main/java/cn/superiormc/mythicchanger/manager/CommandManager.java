package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.commands.*;
import cn.superiormc.mythicchanger.objects.AbstractCommand;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandManager {

    public static CommandManager commandManager;

    private Map<String, AbstractCommand> registeredCommands = new HashMap<>();

    public CommandManager(){
        commandManager = this;
        registerBukkitCommand();
        registerObjectCommand();
    }

    private void registerBukkitCommand(){
        Objects.requireNonNull(Bukkit.getPluginCommand("mythicchanger")).setExecutor(new MainCommand());
        Objects.requireNonNull(Bukkit.getPluginCommand("mythicchanger")).setTabCompleter(new MainCommandTab());
    }

    private void registerObjectCommand() {
        if (!MythicChanger.freeVersion) {
            if (CommonUtil.checkPluginLoad("NBTAPI")) {
                registeredCommands.put("viewnbt", new SubViewNBT());
            }
            if (!ConfigManager.configManager.getString("apply-item-mode", "").equalsIgnoreCase("DRAG") ||
                    ConfigManager.configManager.getBoolean("apply-item-mode.gui-enabled", true)) {
                registeredCommands.put("openchangegui", new SubOpenChangeGUI());
            }
            registeredCommands.put("setlimit", new SubSetLimit());
        }
        registeredCommands.put("help", new SubHelp());
        registeredCommands.put("reload", new SubReload());
        registeredCommands.put("saveitem", new SubSaveItem());
        registeredCommands.put("givesaveitem", new SubGiveSaveItem());
        registeredCommands.put("generateitemformat", new SubGenerateItemFormat());
        registeredCommands.put("giveapplyitem", new SubGiveApplyItem());
    }

    public Map<String, AbstractCommand> getSubCommandsMap() {
        return registeredCommands;
    }
}

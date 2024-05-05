package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.commands.*;
import cn.superiormc.mythicchanger.objects.ObjectCommand;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandManager {

    public static CommandManager commandManager;

    private Map<String, ObjectCommand> registeredCommands = new HashMap<>();

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
            registeredCommands.put("giveapplyitem", new SubGiveApplyItem());
        }
        registeredCommands.put("help", new SubHelp());
        registeredCommands.put("reload", new SubReload());
        registeredCommands.put("saveitem", new SubSaveItem());
        registeredCommands.put("generateitemformat", new SubGenerateItemFormat());
    }

    public Map<String, ObjectCommand> getSubCommandsMap() {
        return registeredCommands;
    }
}

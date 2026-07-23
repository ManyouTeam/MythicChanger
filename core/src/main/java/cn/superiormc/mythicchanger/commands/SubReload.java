package cn.superiormc.mythicchanger.commands;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.ItemManager;
import cn.superiormc.mythicchanger.manager.LanguageManager;
import cn.superiormc.mythicchanger.objects.AbstractCommand;
import org.bukkit.entity.Player;

public class SubReload extends AbstractCommand {

    public SubReload() {
        this.id = "reload";
        this.requiredPermission =  "mythicchanger." + id;
        this.onlyInGame = false;
        this.requiredArgLength = new Integer[]{1};
    }

    /* Usage:

    /mc reload

     */
    @Override
    public void executeCommandInGame(String[] args, Player player) {
        MythicChanger.instance.reloadConfig();
        new ConfigManager();
        new ItemManager();
        //ChangesManager.changesManager.clearCooldown();
        LanguageManager.languageManager.sendStringText(player, "plugin.reloaded");
    }

    @Override
    public void executeCommandInConsole(String[] args) {
        MythicChanger.instance.reloadConfig();
        new ConfigManager();
        new ItemManager();
        //ChangesManager.changesManager.clearCooldown();
        LanguageManager.languageManager.sendStringText("plugin.reloaded");
    }
}

package cn.superiormc.mythicchanger.commands;

import cn.superiormc.mythicchanger.manager.LanguageManager;
import cn.superiormc.mythicchanger.objects.AbstractCommand;
import org.bukkit.entity.Player;

public class SubHelp extends AbstractCommand {

    public SubHelp() {
        this.id = "help";
        this.onlyInGame = false;
        this.requiredArgLength = new Integer[]{1};
    }

    @Override
    public void executeCommandInGame(String[] args, Player player) {
        if (player.hasPermission("mythicchanger.admin")) {
            LanguageManager.languageManager.sendStringText(player, "help.main-admin");
            return;
        }
        LanguageManager.languageManager.sendStringText(player, "help.main");
    }

    @Override
    public void executeCommandInConsole(String[] args) {
        LanguageManager.languageManager.sendStringText("help.main-console");
    }
}

package cn.superiormc.mythicchanger.commands;

import cn.superiormc.mythicchanger.gui.ChangeGUI;
import cn.superiormc.mythicchanger.manager.LanguageManager;
import cn.superiormc.mythicchanger.objects.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SubOpenChangeGUI extends AbstractCommand {


    public SubOpenChangeGUI() {
        this.id = "openchangegui";
        this.requiredPermission =  "mythicchanger." + id;
        this.onlyInGame = false;
        this.requiredArgLength = new Integer[]{1, 2};
    }

    @Override
    public void executeCommandInGame(String[] args, Player player) {
        if (args.length > 1) {
            LanguageManager.languageManager.sendStringText(player, "error.args");
            return;
        }
        ChangeGUI gui = new ChangeGUI(player);
        gui.openGUI();
    }

    @Override
    public void executeCommandInConsole(String[] args) {
        Player whoWillAdd = Bukkit.getPlayer(args[1]);
        if (whoWillAdd == null) {
            LanguageManager.languageManager.sendStringText("error.player-not-found", "player", args[1]);
            return;
        }
        ChangeGUI gui = new ChangeGUI(whoWillAdd);
        gui.openGUI();
    }
}

package cn.superiormc.mythicchanger.commands;

import cn.superiormc.mythicchanger.gui.ChangeGUI;
import cn.superiormc.mythicchanger.manager.LanguageManager;
import cn.superiormc.mythicchanger.objects.AbstractCommand;
import cn.superiormc.mythicchanger.objects.ObjectApplyItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SubOpenChangeGUI extends AbstractCommand {

    public SubOpenChangeGUI() {
        this.id = "openchangegui";
        this.requiredPermission =  "mythicchanger." + id;
        this.onlyInGame = false;
        this.requiredArgLength = new Integer[]{1, 2, 3};
    }

    @Override
    public void executeCommandInGame(String[] args, Player player) {
        if (args.length > 2) {
            LanguageManager.languageManager.sendStringText(player, "error.args");
            return;
        }
        String customMode = "GUI";
        if (args.length == 2) {
            if (player.hasPermission(requiredPermission + "." + args[1])) {
                customMode = args[1];
            } else {
                LanguageManager.languageManager.sendStringText(player, "error.miss-permission");
                return;
            }
        }
        ChangeGUI gui = new ChangeGUI(player, customMode);
        gui.openGUI();
    }

    @Override
    public void executeCommandInConsole(String[] args) {
        if (args.length == 1) {
            LanguageManager.languageManager.sendStringText("error.args");
            return;
        }
        Player whoWillAdd = Bukkit.getPlayer(args[1]);
        if (whoWillAdd == null) {
            LanguageManager.languageManager.sendStringText("error.player-not-found", "player", args[1]);
            return;
        }
        String customMode = "GUI";
        if (args.length == 3) {
            customMode = args[2];
        }
        ChangeGUI gui = new ChangeGUI(whoWillAdd, customMode);
        gui.openGUI();
    }

    @Override
    public List<String> getTabResult(int length) {
        List<String> tempVal1 = new ArrayList<>();
        switch (length) {
            case 2:
                tempVal1.add("GUI");
                tempVal1.addAll(ObjectApplyItem.customModes);
                break;
        }
        return tempVal1;
    }
}

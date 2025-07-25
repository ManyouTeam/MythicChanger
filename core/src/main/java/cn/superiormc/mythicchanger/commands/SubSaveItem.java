package cn.superiormc.mythicchanger.commands;

import cn.superiormc.mythicchanger.manager.*;
import cn.superiormc.mythicchanger.objects.AbstractCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SubSaveItem extends AbstractCommand {

    public SubSaveItem() {
        this.id = "saveitem";
        this.requiredPermission =  "mythicchanger." + id;
        this.onlyInGame = true;
        this.requiredArgLength = new Integer[]{2, 3};
    }

    @Override
    public void executeCommandInGame(String[] args, Player player) {
        if (args.length == 2 || args[args.length - 1].equalsIgnoreCase("bukkit")) {
            ItemManager.itemManager.saveMainHandItem(player, args[1]);
        } else if (args[args.length - 1].equalsIgnoreCase("itemformat")) {
            ItemManager.itemManager.saveMainHandItemFormat(player, args[1]);
        }
        LanguageManager.languageManager.sendStringText(player, "plugin.saved");
    }

    @Override
    public List<String> getTabResult(int length) {
        List<String> tempVal1 = new ArrayList<>();
        switch (length) {
            case 2:
                tempVal1.add(LanguageManager.languageManager.getStringText("command-tab.type-item-id"));
                break;
            case 3:
                tempVal1.add("bukkit");
                tempVal1.add("itemformat");
        }
        return tempVal1;
    }
}

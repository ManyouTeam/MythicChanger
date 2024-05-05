package cn.superiormc.mythicchanger.commands;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.*;
import cn.superiormc.mythicchanger.objects.ObjectCommand;
import org.bukkit.entity.Player;

public class SubSaveItem extends ObjectCommand {


    public SubSaveItem() {
        this.id = "saveitem";
        this.requiredPermission =  "mythicchanger." + id;
        this.onlyInGame = true;
        this.requiredArgLength = new Integer[]{2};
    }

    /* Usage:

    /mc reload

     */
    @Override
    public void executeCommandInGame(String[] args, Player player) {
        ItemManager.itemManager.saveMainHandItem(player, args[1]);
        LanguageManager.languageManager.sendStringText(player, "plugin.saved");
    }
}

package cn.superiormc.mythicchanger.commands;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.LanguageManager;
import cn.superiormc.mythicchanger.objects.ObjectCommand;
import cn.superiormc.mythicchanger.objects.ObjectSingleRule;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SubApplyRealRule extends ObjectCommand {


    public SubApplyRealRule() {
        this.id = "applyfakerule";
        this.requiredPermission =  "mythicchanger." + id;
        this.onlyInGame = true;
        this.requiredArgLength = new Integer[]{2};
    }

    /* Usage:

    /prefix reload

     */
    @Override
    public void executeCommandInGame(String[] args, Player player) {
        ObjectSingleRule rule = ConfigManager.configManager.getRule(args[1]);
        if (rule == null) {
            LanguageManager.languageManager.sendStringText(player, "error.rule-not-found");
            return;
        }
        rule.setRealChange(player.getInventory().getItemInMainHand(), player);
        LanguageManager.languageManager.sendStringText(player, "plugin.changed");
    }

    @Override
    public List<String> getTabResult(int length) {
        List<String> tempVal1 = new ArrayList<>();
        if (length == 2) {
            for (ObjectSingleRule rule : ConfigManager.configManager.ruleCaches) {
                tempVal1.add(rule.getId());
            }
        }
        return tempVal1;
    }
}

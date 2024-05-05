package cn.superiormc.mythicchanger.commands;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.LanguageManager;
import cn.superiormc.mythicchanger.objects.ObjectApplyItem;
import cn.superiormc.mythicchanger.objects.ObjectCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SubGiveApplyItem extends ObjectCommand {


    public SubGiveApplyItem() {
        this.id = "giveapplyitem";
        this.requiredPermission =  "mythicchanger." + id;
        this.onlyInGame = false;
        this.requiredArgLength = new Integer[]{2, 3, 4};
    }

    /* Usage:

    /mc giveapplyitem <item> <player> <amount>

     */
    @Override
    public void executeCommandInGame(String[] args, Player player) {
        ObjectApplyItem applyItem = ConfigManager.configManager.getApplyItem(args[1]);
        if (applyItem == null) {
            LanguageManager.languageManager.sendStringText(player, "error.item-not-found", "item", args[1]);
            return;
        }
        Player givePlayer = player;
        if (args.length >= 3) {
            givePlayer = Bukkit.getPlayer(args[2]);
        }
        if (givePlayer == null) {
            LanguageManager.languageManager.sendStringText(player, "error.player-not-found", "player", args[2]);
            return;
        }
        int amount = 1;
        if (args.length == 4) {
            amount = Integer.parseInt(args[3]);
        }
        ItemStack item = applyItem.getApplyItem();
        if (item != null) {
            item.setAmount(amount);
            givePlayer.getInventory().addItem(item);
            LanguageManager.languageManager.sendStringText(player, "plugin.item-gave", "item", args[1],
                    "player", givePlayer.getName(), "amount", String.valueOf(amount));
        }
    }

    @Override
    public void executeCommandInConsole(String[] args) {
        if (args.length < 3) {
            LanguageManager.languageManager.sendStringText("error.args");
            return;
        }
        ObjectApplyItem applyItem = ConfigManager.configManager.getApplyItem(args[1]);
        if (applyItem == null) {
            LanguageManager.languageManager.sendStringText("error.item-not-found", "item", args[1]);
            return;
        }
        Player givePlayer = Bukkit.getPlayer(args[2]);
        if (givePlayer == null) {
            LanguageManager.languageManager.sendStringText("error.player-not-found", "player", args[2]);
            return;
        }
        int amount = 1;
        if (args.length == 4) {
            amount = Integer.parseInt(args[3]);
        }
        ItemStack item = applyItem.getApplyItem();
        if (item != null) {
            item.setAmount(amount);
            givePlayer.getInventory().addItem(item);
            LanguageManager.languageManager.sendStringText("plugin.item-gave", "item", args[1],
                    "player", givePlayer.getName(), "amount", String.valueOf(amount));
        }
    }

    @Override
    public List<String> getTabResult(int length) {
        List<String> tempVal1 = new ArrayList<>();
        if (length == 2) {
            tempVal1.addAll(ConfigManager.configManager.itemMap.keySet());
        }
        else if (length == 3) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                tempVal1.add(player.getName());
            }
        } else if (length == 4) {
            tempVal1.add("[amount]");
        }
        return tempVal1;
    }
}

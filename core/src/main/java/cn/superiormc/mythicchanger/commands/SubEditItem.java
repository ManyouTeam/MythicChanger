package cn.superiormc.mythicchanger.commands;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.LanguageManager;
import cn.superiormc.mythicchanger.objects.AbstractCommand;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubEditItem extends AbstractCommand {

    public SubEditItem() {
        this.id = "edititem";
        this.requiredPermission = "mythicchanger." + id;
        this.onlyInGame = true;
        this.requiredArgLength = new Integer[]{3};
        this.unlimitedArgLength = true;
    }

    @Override
    public void executeCommandInGame(String[] args, Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!ItemUtil.isValid(item) || item.getItemMeta() == null) {
            LanguageManager.languageManager.sendStringText(player, "error.item-empty");
            return;
        }

        ItemMeta meta = item.getItemMeta();
        switch (args[1].toLowerCase()) {
            case "name":
                MythicChanger.methodUtil.setItemName(meta, args[2], player);
                break;
            case "itemname":
                MythicChanger.methodUtil.setItemItemName(meta, args[2], player);
                break;
            case "lore":
                MythicChanger.methodUtil.setItemLore(meta, Arrays.asList(Arrays.copyOfRange(args, 2, args.length)), player);
                break;
            default:
                LanguageManager.languageManager.sendStringText(player, "error.args");
                return;
        }
        item.setItemMeta(meta);
        LanguageManager.languageManager.sendStringText(player, "plugin.edited");
    }

    @Override
    public List<String> getTabResult(int length, Player player) {
        List<String> tempVal1 = new ArrayList<>();
        switch (length) {
            case 2:
                tempVal1.add("name");
                tempVal1.add("itemname");
                tempVal1.add("lore");
                break;
            case 3:
                tempVal1.add("[value]");
                break;
        }
        return tempVal1;
    }
}

package cn.superiormc.mythicchanger.commands;

import cn.superiormc.mythicchanger.manager.LanguageManager;
import cn.superiormc.mythicchanger.objects.AbstractCommand;
import cn.superiormc.mythicchanger.objects.ObjectApplyItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SubSetLimit extends AbstractCommand {

    public SubSetLimit() {
        this.id = "setlimit";
        this.requiredPermission =  "mythicchanger." + id;
        this.onlyInGame = true;
        this.requiredArgLength = new Integer[]{2};
    }

    @Override
    public void executeCommandInGame(String[] args, Player player) {
        int amount = Integer.parseInt(args[1]);
        ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
        if (meta == null) {
            LanguageManager.languageManager.sendStringText(player, "error.item-empty");
            return;
        }
        meta.getPersistentDataContainer().set(ObjectApplyItem.MYTHICCHANGER_APPLY_LIMIT, PersistentDataType.INTEGER, amount);
        player.getInventory().getItemInMainHand().setItemMeta(meta);
        LanguageManager.languageManager.sendStringText(player, "success-set-limit", "limit", String.valueOf(amount));
    }
}

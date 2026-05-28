package cn.superiormc.mythicchanger.commands;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.LanguageManager;
import cn.superiormc.mythicchanger.objects.AbstractCommand;
import cn.superiormc.mythicchanger.objects.ObjectApplyItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SubViewApplyRules extends AbstractCommand {

    public SubViewApplyRules() {
        this.id = "viewapplyrules";
        this.requiredPermission =  "mythicchanger." + id;
        this.onlyInGame = true;
        this.requiredArgLength = new Integer[]{1};
    }

    @Override
    public void executeCommandInGame(String[] args, Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().isAir()) {
            LanguageManager.languageManager.sendStringText(player, "error.item-empty");
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.getPersistentDataContainer().has(ObjectApplyItem.MYTHICCHANGER_APPLY_RULE,
                PersistentDataType.STRING)) {
            LanguageManager.languageManager.sendStringText(player, "apply-rule-view-empty");
            return;
        }
        String rawRules = meta.getPersistentDataContainer().get(ObjectApplyItem.MYTHICCHANGER_APPLY_RULE,
                PersistentDataType.STRING);
        if (rawRules == null || rawRules.isEmpty()) {
            LanguageManager.languageManager.sendStringText(player, "apply-rule-view-empty");
            return;
        }
        LanguageManager.languageManager.sendStringText(player, "apply-rule-view", "rules", rawRules);
        for (String applyItemID : rawRules.split(";;")) {
            if (applyItemID.isEmpty()) {
                continue;
            }
            if (ConfigManager.configManager.getApplyItem(applyItemID) == null) {
                LanguageManager.languageManager.sendStringText(player, "apply-rule-view-item-missing",
                        "rule", applyItemID);
                continue;
            }
            LanguageManager.languageManager.sendStringText(player, "apply-rule-view-item",
                    "rule", applyItemID);
        }
    }
}

package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.ErrorManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AddNameFirst extends AbstractChangesRule {

    public AddNameFirst() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (!singleChange.isFakeOrReal() && !ConfigManager.configManager.getBoolean("bypass-real-change-limit")) {
            ErrorManager.errorManager.sendErrorMessage("§cError: add-name-first rule only supports" +
                    " fake change, please remove it in real changes from all your rule configs! If you want to bypass this limit, " +
                    "please disable limit check in config.yml file.");
            return singleChange.getItem();
        }
        if (ConfigManager.configManager.getBoolean("keep-name-in-anvil") && !singleChange.isPlayerInventory() &&
                singleChange.getPlayer().getOpenInventory().getType().equals(InventoryType.ANVIL)) {
            return singleChange.getItem();
        }
        ItemMeta meta = singleChange.getItemMeta();
        String tempVal1;
        if (!meta.hasDisplayName()) {
            tempVal1 = "§f" + singleChange.getString("add-name-first") + ItemUtil.getItemName(singleChange.getItem());
        } else {
            tempVal1 = singleChange.getString("add-name-first") + ItemUtil.getItemName(singleChange.getItem());
        }
        MythicChanger.methodUtil.setItemName(meta, tempVal1, singleChange.getPlayer());
        return singleChange.setItemMeta(meta);
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("add-name-first", 21);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getString("add-name-first") == null;
    }
}

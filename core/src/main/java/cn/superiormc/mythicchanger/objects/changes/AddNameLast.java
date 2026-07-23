package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AddNameLast extends AbstractChangesRule {

    public AddNameLast() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (ConfigManager.configManager.getBoolean("keep-name-in-anvil") && !singleChange.isPlayerInventory() &&
                singleChange.getPlayer().getOpenInventory().getType().equals(InventoryType.ANVIL)) {
            return singleChange.getItem();
        }
        ItemMeta meta = singleChange.getItemMeta();
        String tempVal1;
        if (!meta.hasDisplayName()) {
            tempVal1 = "§f" + ItemUtil.getItemName(singleChange.getItem()) + singleChange.getString("add-name-last");
        } else {
            tempVal1 = ItemUtil.getItemName(singleChange.getItem()) + singleChange.getString("add-name-last");
        }
        MythicChanger.methodUtil.setItemName(meta, tempVal1, singleChange.getPlayer());
        return singleChange.setItemMeta(meta);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getString("add-name-last") == null;
    }
}

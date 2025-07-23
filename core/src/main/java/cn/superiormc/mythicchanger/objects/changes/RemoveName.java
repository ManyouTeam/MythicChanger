package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RemoveName extends AbstractChangesRule {

    public RemoveName() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (!singleChange.getBoolean("remove-name")) {
            return singleChange.getItem();
        }
        ItemMeta meta = singleChange.getItemMeta();
        if (meta.hasDisplayName()) {
            MythicChanger.methodUtil.setItemName(meta, null, singleChange.getPlayer());
        }
        singleChange.setItemMeta(meta);
        return singleChange.getItem();
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("remove-name", -300);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("remove-name");
    }
}

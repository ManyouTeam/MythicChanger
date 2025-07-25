package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.ErrorManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MIUpdateLore extends AbstractChangesRule {

    public MIUpdateLore() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
       if (singleChange.getBoolean("mi-update-lore")) {
            String id = MMOItems.getID(singleChange.getItem());
            if (id != null && !id.isEmpty()) {
                MMOItem mmoItem = new LiveMMOItem(singleChange.getItem());
                ItemStack result = mmoItem.newBuilder().build();
                if (result == null) {
                    return singleChange.getItem();
                }
                ItemMeta meta = result.getItemMeta();
                if (meta == null) {
                    return singleChange.getItem();
                }
                ItemMeta originalMeta = singleChange.getItemMeta();
                if (meta.hasLore()) {
                    MythicChanger.methodUtil.setItemLore(originalMeta, MythicChanger.methodUtil.getItemLore(meta), singleChange.getPlayer());
                }
                return singleChange.setItemMeta(originalMeta);
            } else {
                if (!ConfigManager.configManager.getBoolean("ignore-warning")) {
                    ErrorManager.errorManager.sendErrorMessage("§6Warning: mi-update-lore rule only working for " +
                            "items from MMOItems plugin, please adjust your match-item configuration to ensure that only items from MMOItems will use this rule." +
                            " If you want to ignore this warning, please disable warning display in config.yml file.");
                }
            }
        }
        return singleChange.getItem();
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("mi-update-lore", 115);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("mi-update-lore");
    }
}

package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Rarity extends AbstractMatchItemRule {

    public Rarity() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, Player player, ItemStack item, ItemMeta meta) {
        String result;
        if (meta.hasRarity()) {
            ItemRarity rarity = meta.getRarity();
            result = rarity.name();
        } else {
            result = "NONE";
        }
        if (ConfigManager.configManager.getBoolean("debug")) {
            TextUtil.sendMessage(null, TextUtil.pluginPrefix() + " 鎼俢Rarity: " + result);
        }
        return result.equalsIgnoreCase(getString(section, "rarity", player));
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getString("rarity") == null;
    }
}

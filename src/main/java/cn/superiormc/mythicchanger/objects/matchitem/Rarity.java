package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Rarity extends AbstractMatchItemRule {

    public Rarity() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        String result;
        if (meta.hasRarity()) {
            ItemRarity rarity = meta.getRarity();
            result = rarity.name();
        } else {
            result = "NONE";
        }
        if (ConfigManager.configManager.getBoolean("debug")) {
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §cRarity: " + result);
        }
        return result.equalsIgnoreCase(section.getString("rarity"));
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getString("rarity") == null;
    }
}

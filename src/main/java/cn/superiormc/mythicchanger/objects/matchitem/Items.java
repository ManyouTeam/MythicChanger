package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.HookManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items extends AbstractMatchItemRule {
    public Items() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        if (ConfigManager.configManager.getBoolean("debug")) {
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §cItem ID: " +
                    HookManager.hookManager.parseItemID(item, section.getBoolean("use-tier-identify", false)));
        }
        return section.getStringList("items").contains(
                HookManager.hookManager.parseItemID(item, section.getBoolean("use-tier-identify", false)));
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("items").isEmpty();
    }
}

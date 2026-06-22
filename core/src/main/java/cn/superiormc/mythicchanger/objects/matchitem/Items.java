package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.HookManager;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items extends AbstractMatchItemRule {

    public Items() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        return getMatch(section, null, item, meta);
    }

    @Override
    public boolean getMatch(ConfigurationSection section, Player player, ItemStack item, ItemMeta meta) {
        if (ConfigManager.configManager.getBoolean("debug")) {
            TextUtil.sendMessage(null, TextUtil.pluginPrefix() + " §cItem ID: " +
                    HookManager.hookManager.parseItemID(item, section.getBoolean("use-tier-identify", false)));
        }
        return getStringList(section, "items", player).contains(
                HookManager.hookManager.parseItemID(item, section.getBoolean("use-tier-identify", false)));
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("items").isEmpty();
    }
}

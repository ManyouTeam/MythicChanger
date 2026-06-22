package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractMatchItemRule {

    public AbstractMatchItemRule() {
        // Empty...
    }

    public abstract boolean getMatch(ConfigurationSection section, Player player, ItemStack item, ItemMeta meta);
    
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        return getMatch(section, null, item, meta);
    }

    public abstract boolean configNotContains(ConfigurationSection section);

    protected String getString(ConfigurationSection section, String path, Player player) {
        String result = section.getString(path);
        return result == null ? null : TextUtil.withPAPI(result, player);
    }

    protected String getString(ConfigurationSection section, String path, Player player, String defaultValue) {
        String result = section.getString(path, defaultValue);
        return result == null ? null : TextUtil.withPAPI(result, player);
    }

    protected List<String> getStringList(ConfigurationSection section, String path, Player player) {
        return section.getStringList(path)
                .stream()
                .map(text -> TextUtil.withPAPI(text, player))
                .collect(Collectors.toList());
    }

    protected Set<String> getKeys(ConfigurationSection section, Player player) {
        Set<String> result = new LinkedHashSet<>();
        for (String key : section.getKeys(false)) {
            result.add(TextUtil.withPAPI(key, player));
        }
        return result;
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}

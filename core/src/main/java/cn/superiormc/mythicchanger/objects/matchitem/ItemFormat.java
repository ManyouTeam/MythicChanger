package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.methods.DebuildItem;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class ItemFormat extends AbstractMatchItemRule {

    public ItemFormat() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, Player player, ItemStack item, ItemMeta meta) {
        Map<String, Object> item1Result = DebuildItem.debuildItem(item, new MemoryConfiguration()).getValues(true);
        Map<String, Object> item2Result = section.getConfigurationSection("item-format").getValues(true);
        if (section.getBoolean("item-format-settings.require-same-key")) {
            for (String key : item1Result.keySet()) {
                if (canIgnore(key, section, player)) {
                    continue;
                }
                if (!item2Result.containsKey(key)) {
                    return false;
                }
            }
        }
        for (String key : item2Result.keySet()) {
            if (canIgnore(key, section, player)) {
                continue;
            }
            Object object = item1Result.get(key);
            if (object == null) {
                return false;
            }
            if (object instanceof MemorySection) {
                continue;
            }
            Object requiredObject = item2Result.get(key);
            if (requiredObject instanceof String) {
                requiredObject = CommonUtil.parseLang(player, (String) requiredObject);
            }
            if (!object.equals(requiredObject)) {
                if (object instanceof String tempVal1 && requiredObject instanceof String tempVal2) {
                    if (tempVal1.equalsIgnoreCase(tempVal2)) {
                        continue;
                    }
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("item-format") == null;
    }

    private boolean canIgnore(String key, ConfigurationSection section, Player player) {
        if (key == null) {
            return true;
        }
        if (key.equals("amount")) {
            return true;
        }
        for (String tempVal1 : getStringList(section, "item-format-settings.ignore-key", player)) {
            if (tempVal1.equals(key) || key.startsWith(tempVal1 + ".")) {
                return true;
            }
        }
        return false;
    }
}

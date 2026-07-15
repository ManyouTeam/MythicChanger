package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class ReplaceName extends AbstractChangesRule {

    public ReplaceName() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ObjectSingleChange tempVal1 = singleChange.getConfigurationSection("replace-name");
        ItemMeta meta = singleChange.getItemMeta();
        if (tempVal1 == null || !meta.hasLore()) {
            return singleChange.getItem();
        }
        String displayName = MythicChanger.methodUtil.getItemName(meta);
        for (Map.Entry<String, Object> entry : tempVal1.section.getValues(true).entrySet()) {
            if (entry.getValue() instanceof ConfigurationSection) {
                continue;
            }
            String requiredName = TextUtil.withPAPI(singleChange.parsePlaceholder(entry.getKey()),
                    singleChange.getPlayer());
            String replacement = entry.getValue() == null ? "" : entry.getValue().toString();
            replacement = TextUtil.withPAPI(singleChange.parsePlaceholder(replacement), singleChange.getPlayer());
            displayName = displayName.replace(requiredName, replacement);
        }
        MythicChanger.methodUtil.setItemName(meta, displayName, singleChange.getPlayer());
        return singleChange.setItemMeta(meta);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("replace-name") == null;
    }
}

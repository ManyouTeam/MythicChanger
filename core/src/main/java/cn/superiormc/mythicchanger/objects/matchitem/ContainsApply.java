package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.objects.ObjectApplyItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;

public class ContainsApply extends AbstractMatchItemRule {

    public ContainsApply() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, Player player, ItemStack item, ItemMeta meta) {
        Collection<ObjectApplyItem> applyItems = ObjectApplyItem.getRule(meta);
        for (ObjectApplyItem applyItem : applyItems) {
            for (String requiredLore : getStringList(section, "contains-apply", player)) {
                if (requiredLore.equals(applyItem.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("contains-apply") == null;
    }
}

package cn.superiormc.mythicchanger.objects.matchitem;

import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MaterialTag extends AbstractMatchItemRule {

    public MaterialTag() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, Player player, ItemStack item, ItemMeta meta) {
        for (String singleMaterial : getStringList(section, "material-tag", player)) {
            Tag<Material> tempVal1 = Bukkit.getTag(Tag.REGISTRY_ITEMS, CommonUtil.parseNamespacedKey(singleMaterial), Material.class);
            if (tempVal1 != null && tempVal1.isTagged(item.getType())) {
                return true;
            }
            Tag<Material> tempVal2 = Bukkit.getTag(Tag.REGISTRY_BLOCKS, CommonUtil.parseNamespacedKey(singleMaterial), Material.class);
            if (tempVal2 != null && tempVal2.isTagged(item.getType())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getStringList("material-tag").isEmpty();
    }
}

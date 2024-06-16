package cn.superiormc.mythicchanger.objects.changes;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KeepLore extends AbstractChangesRule {

    public KeepLore() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section, ItemStack original, ItemStack item, Player player, boolean fakeOrReal) {
        if (section.getBoolean("keep-lore", false)) {
            ItemMeta meta = item.getItemMeta();
            ItemMeta originalMeta = original.getItemMeta();
            if (meta == null || originalMeta == null) {
                return item;
            }
            if (originalMeta.hasLore()) {
                meta.setLore(originalMeta.getLore());
            }
            item.setItemMeta(meta);
            return item;
        }
        return item;
    }

    @Override
    public int getWeight() {
        return 1005;
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.get("keep-lore") == null;
    }
}

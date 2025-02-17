package cn.superiormc.mythicchanger.objects.matchitem;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ContainsEnchantsAmount extends AbstractMatchItemRule {
    public ContainsEnchantsAmount() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, ItemStack item, ItemMeta meta) {
        if (section.getString("contains-enchants-amount", "").startsWith("[")) {
            return section.getIntegerList("contains-enchants-amount").contains(meta.getEnchants().size());
        }
        return meta.getEnchants().size() >= section.getInt("contains-enchants-amount");
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("contains-enchants-amount");
    }
}

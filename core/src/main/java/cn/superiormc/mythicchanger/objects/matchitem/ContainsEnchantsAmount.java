package cn.superiormc.mythicchanger.objects.matchitem;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ContainsEnchantsAmount extends AbstractMatchItemRule {

    public ContainsEnchantsAmount() {
        super();
    }

    @Override
    public boolean getMatch(ConfigurationSection section, Player player, ItemStack item, ItemMeta meta) {
        int size;
        if (meta instanceof EnchantmentStorageMeta) {
            EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) meta;
            size = enchantmentStorageMeta.getStoredEnchants().size();
        } else {
            size = meta.getEnchants().size();
        }
        if (section.isList("contains-enchants-amount")) {
            return section.getIntegerList("contains-enchants-amount").contains(size);
        }
        String requiredAmount = getString(section, "contains-enchants-amount", player, "");
        if (requiredAmount.startsWith("[")) {
            return Arrays.stream(requiredAmount.replace("[", "").replace("]", "").split(","))
                    .map(String::trim)
                    .filter(value -> !value.isEmpty())
                    .map(Integer::parseInt)
                    .anyMatch(value -> value == size);
        }
        return size >= Integer.parseInt(requiredAmount);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("contains-enchants-amount");
    }
}

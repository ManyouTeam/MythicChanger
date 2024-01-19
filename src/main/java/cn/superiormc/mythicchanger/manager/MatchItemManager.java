package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.objects.matchitem.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashSet;

public class MatchItemManager {

    public static MatchItemManager matchItemManager;

    private Collection<AbstractMatchItemRule> rules = new HashSet<>();

    public MatchItemManager() {
        matchItemManager = this;
        registerNewMatchItemRule(new ContainsLore());
        registerNewMatchItemRule(new ContainsName());
        registerNewMatchItemRule(new HasEnchants());
        registerNewMatchItemRule(new HasLore());
        registerNewMatchItemRule(new HasName());
        registerNewMatchItemRule(new Items());
    }

    public void registerNewMatchItemRule(AbstractMatchItemRule rule) {
        rules.add(rule);
    }

    public boolean getMatch(ConfigurationSection section, ItemStack item) {
        for (AbstractMatchItemRule rule : rules) {
            if (!rule.getMatch(section, item)) {
                return false;
            }
        }
        return true;
    }

}

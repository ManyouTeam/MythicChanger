package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.objects.changes.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

public class ChangesManager {

    public static ChangesManager changesManager;

    //private Collection<Player> itemCooldown = new HashSet<>();

    private Collection<AbstractChangesRule> rules = new TreeSet<>();

    public ChangesManager() {
        changesManager = this;
        registerNewMatchItemRule(new AddEnchants());
        registerNewMatchItemRule(new AddFlags());
        registerNewMatchItemRule(new AddLoreFirst());
        registerNewMatchItemRule(new AddLoreLast());
        registerNewMatchItemRule(new AddLorePrefix());
        registerNewMatchItemRule(new AddLoreSuffix());
        registerNewMatchItemRule(new AddNameFirst());
        registerNewMatchItemRule(new AddNameLast());
        registerNewMatchItemRule(new DeleteEnchants());
        registerNewMatchItemRule(new RemoveAllEnchants());
        registerNewMatchItemRule(new RemoveFlags());
        registerNewMatchItemRule(new RemoveEnchants());
        registerNewMatchItemRule(new ReplaceItem());
        registerNewMatchItemRule(new ReplaceLore());
        registerNewMatchItemRule(new SetCustomModelData());
        registerNewMatchItemRule(new SetLore());
        registerNewMatchItemRule(new SetName());
        registerNewMatchItemRule(new SetType());
    }

    public void registerNewMatchItemRule(AbstractChangesRule rule) {
        rules.add(rule);
    }

    public ItemStack setFakeChange(ConfigurationSection section, ItemStack item, Player player) {
        for (AbstractChangesRule rule : rules) {
            item = rule.setChange(section, item, player);
        }
        return item;
    }

    public ItemStack setRealChange(ConfigurationSection section, ItemStack item, Player player) {
        boolean needReturnNewItem = false;
        for (AbstractChangesRule rule : rules) {
            if (rule.getNeedRewriteItem()) {
                needReturnNewItem = true;
            }
            rule.setChange(section, item, player);
        }
        if (needReturnNewItem) {
            //itemCooldown.add(player);
            //Bukkit.getScheduler().runTaskLater(MythicChanger.instance, () -> {
            //    itemCooldown.remove(player);
            //}, 5L);
            return item;
        }
        return null;
    }

    //public Collection<Player> getItemCooldown() {
        //return itemCooldown;
    //}

}

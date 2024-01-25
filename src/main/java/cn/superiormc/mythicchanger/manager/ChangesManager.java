package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.objects.changes.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.TreeSet;

public class ChangesManager {

    public static ChangesManager changesManager;

    //private Collection<Player> itemCooldown = new HashSet<>();

    private Collection<AbstractChangesRule> rules = new TreeSet<>();

    public ChangesManager() {
        changesManager = this;
        registerNewRule(new AddEnchants());
        registerNewRule(new AddFlags());
        registerNewRule(new AddLoreFirst());
        registerNewRule(new AddLoreLast());
        registerNewRule(new AddLorePrefix());
        registerNewRule(new AddLoreSuffix());
        registerNewRule(new AddNameFirst());
        registerNewRule(new AddNameLast());
        registerNewRule(new DeleteEnchants());
        registerNewRule(new RemoveAllEnchants());
        registerNewRule(new RemoveFlags());
        registerNewRule(new RemoveEnchants());
        registerNewRule(new ReplaceItem());
        registerNewRule(new ReplaceLore());
        registerNewRule(new SetCustomModelData());
        registerNewRule(new SetLore());
        registerNewRule(new SetName());
        registerNewRule(new SetType());
    }

    public void registerNewRule(AbstractChangesRule rule) {
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

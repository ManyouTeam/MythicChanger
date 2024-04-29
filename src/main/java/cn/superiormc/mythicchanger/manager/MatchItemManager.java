package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.objects.matchitem.*;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashSet;

public class MatchItemManager {

    public static MatchItemManager matchItemManager;

    private Collection<AbstractMatchItemRule> rules = new HashSet<>();

    public MatchItemManager() {
        matchItemManager = this;
        registerNewRule(new ContainsLore());
        registerNewRule(new ContainsName());
        registerNewRule(new HasEnchants());
        registerNewRule(new HasLore());
        registerNewRule(new HasName());
        registerNewRule(new Items());
        if (!MythicChanger.freeVersion) {
            if (CommonUtil.checkPluginLoad("NBTAPI")) {
                registerNewRule(new ContainsNBT());
                registerNewRule(new NBTString());
                registerNewRule(new NBTByte());
                registerNewRule(new NBTInt());
                registerNewRule(new NBTDouble());
            }
            registerNewRule(new Not());
        }
    }

    public void registerNewRule(AbstractMatchItemRule rule) {
        rules.add(rule);
    }

    public boolean getMatch(ConfigurationSection section, ItemStack item) {
        if (section == null) {
            return true;
        }
        for (AbstractMatchItemRule rule : rules) {
            if (!rule.getMatch(section, item)) {
                return false;
            }
        }
        return true;
    }

    public Collection<AbstractMatchItemRule> getRules() {
        return rules;
    }
}

package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.objects.ObjectAction;
import cn.superiormc.mythicchanger.objects.matchitem.*;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.HashSet;

public class MatchItemManager {

    public static MatchItemManager matchItemManager;

    private final Collection<AbstractMatchItemRule> rules = new HashSet<>();

    public MatchItemManager() {
        matchItemManager = this;
        initRules();
    }

    private void initRules() {
        registerNewRule(new ContainsLore());
        registerNewRule(new ContainsName());
        registerNewRule(new HasStoredEnchants());
        registerNewRule(new HasEnchants());
        registerNewRule(new HasLore());
        registerNewRule(new HasName());
        registerNewRule(new Items());
        registerNewRule(new Not());
        registerNewRule(new Material());
        registerNewRule(new None());
        registerNewRule(new ItemFormat());
        if (!MythicChanger.freeVersion) {
            if (CommonUtil.checkPluginLoad("NBTAPI")) {
                registerNewRule(new ContainsNBT());
                registerNewRule(new NBTString());
                registerNewRule(new NBTByte());
                registerNewRule(new NBTInt());
                registerNewRule(new NBTDouble());
            }
            if (CommonUtil.getMinorVersion(20, 5)) {
                registerNewRule(new Rarity());
            }
            registerNewRule(new Any());
            registerNewRule(new HasApply());
            registerNewRule(new ContainsEnchants());
            registerNewRule(new ContainsEnchantsAmount());
            registerNewRule(new ContainsApply());
            registerNewRule(new MaterialTag());
            registerNewRule(new Enchantable());
        }
    }

    public void registerNewRule(AbstractMatchItemRule rule) {
        rules.add(rule);
        MythicChanger.methodUtil.sendChat(null, TextUtil.pluginPrefix() + " §fLoaded match rule: " + rule.getClass().getSimpleName() + "!");
    }

    public boolean getMatch(ConfigurationSection section, Player player, ItemStack item) {
        if (section == null) {
            return true;
        }
        if (item == null) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        for (AbstractMatchItemRule rule : rules) {
            if (rule.configNotContains(section)) {
                continue;
            }
            if (!rule.getMatch(section, item, meta)) {
                if (!MythicChanger.freeVersion) {
                    ObjectAction action = new ObjectAction(section.getConfigurationSection("not-match-actions"));
                    action.runAllActions(player, item, item);
                }
                return false;
            }
        }
        return true;
    }

    public Collection<AbstractMatchItemRule> getRules() {
        return rules;
    }
}

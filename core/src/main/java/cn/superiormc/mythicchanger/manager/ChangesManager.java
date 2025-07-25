package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.objects.ObjectAction;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.objects.changes.*;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.SchedulerUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChangesManager {

    public static ChangesManager changesManager;

    private final Map<Player, Collection<Integer>> itemCooldown = new ConcurrentHashMap<>();

    private final Collection<AbstractChangesRule> rules = new TreeSet<>();

    public ChangesManager() {
        changesManager = this;
        initRules();
    }

    private void initRules() {
        registerNewRule(new AddEnchants());
        registerNewRule(new AddFlags());
        registerNewRule(new AddLoreFirst());
        registerNewRule(new AddLoreLast());
        registerNewRule(new AddLorePrefix());
        registerNewRule(new AddLoreSuffix());
        registerNewRule(new AddNameFirst());
        registerNewRule(new AddNameLast());
        registerNewRule(new DeleteEnchants());
        registerNewRule(new Empty());
        registerNewRule(new RemoveAllEnchants());
        registerNewRule(new RemoveFlags());
        registerNewRule(new RemoveEnchants());
        registerNewRule(new ReplaceItem());
        registerNewRule(new ReplaceLore());
        registerNewRule(new SetCustomModelData());
        registerNewRule(new SetLore());
        registerNewRule(new SetName());
        registerNewRule(new SetType());
        registerNewRule(new SetAmount());
        registerNewRule(new FixHideAttributes());
        if (!MythicChanger.freeVersion) {
            registerNewRule(new SetColor());
            if (CommonUtil.checkPluginLoad("NBTAPI")) {
                registerNewRule(new AddNBTString());
                registerNewRule(new AddNBTByte());
                registerNewRule(new AddNBTInt());
                registerNewRule(new AddNBTDouble());
                registerNewRule(new RemoveNBT());
            }
            registerNewRule(new ParsePAPIName());
            registerNewRule(new ParsePAPILore());
            registerNewRule(new EditItem());
            registerNewRule(new KeepEnchants());
            registerNewRule(new KeepDamage());
            registerNewRule(new KeepName());
            registerNewRule(new KeepLore());
            registerNewRule(new KeepFlags());
            if (CommonUtil.getMinorVersion(20, 5)) {
                registerNewRule(new KeepItemName());
            }
            registerNewRule(new ReplaceEnchants());
            registerNewRule(new EditLore());
            registerNewRule(new ReplaceRandomItem());
            registerNewRule(new ReplaceName());
            registerNewRule(new Deapply());
            registerNewRule(new AddApplyLimit());
            registerNewRule(new SetApplyLimit());
            registerNewRule(new ResetApplyLimit());
            registerNewRule(new AddAttributes());
            registerNewRule(new RandomChange());
            registerNewRule(new RemoveName());
            registerNewRule(new RemoveAllLore());
            if (CommonUtil.checkPluginLoad("MMOItems")) {
                registerNewRule(new MIUpdateLore());
            }
        }
    }

    public void registerNewRule(AbstractChangesRule rule) {
        rules.add(rule);
    }

    public ItemStack setFakeChange(ConfigurationSection section, ItemStack item, Player player, boolean isPlayerInventory) {
        ObjectSingleChange singleChange = new ObjectSingleChange(section, item, player, true, isPlayerInventory);
        return setFakeChange(singleChange);
    }

    public ItemStack setFakeChange(ObjectSingleChange singleChange) {
        for (AbstractChangesRule rule : rules) {
            if (rule.configNotContains(singleChange.section)) {
                continue;
            }
            if (singleChange.getItemMeta() == null || singleChange.getOriginalMeta() == null) {
                break;
            }
            if (ConfigManager.configManager.getBoolean("debug")) {
                Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fApply fake rule: " + rule.getClass().getName());
            }
            if (rule.getNeedRewriteItem(singleChange.section)) {
                singleChange.replaceItem(rule.setChange(singleChange));
            } else {
                singleChange.setItem(rule.setChange(singleChange));
            }
        }
        return singleChange.getItem();
    }

    public ItemStack setRealChange(ObjectAction action, ConfigurationSection section, ItemStack item, Player player) {
        ObjectSingleChange singleChange = new ObjectSingleChange(section, item, player, false, true);
        return setRealChange(action, singleChange);
    }

    public ItemStack setRealChange(ObjectAction action, ObjectSingleChange singleChange) {
        boolean needReturnNewItem = false;
        for (AbstractChangesRule rule : rules) {
            if (rule.configNotContains(singleChange.section)) {
                continue;
            }
            if (singleChange.getItemMeta() == null || singleChange.getOriginalMeta() == null) {
                break;
            }
            if (ConfigManager.configManager.getBoolean("debug")) {
                Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fApply real rule: " + rule.getClass().getName());
            }
            if (rule.getNeedRewriteItem(singleChange.section)) {
                singleChange.replaceItem(rule.setChange(singleChange));
                needReturnNewItem = true;
            } else {
                rule.setChange(singleChange);
            }
        }
        if (!MythicChanger.freeVersion && !action.isEmpty()) {
            SchedulerUtil.runSync(() -> action.runAllActions(singleChange.getPlayer(), singleChange.getOriginal(), singleChange.getItem()));
        }
        if (needReturnNewItem ) {
            return singleChange.getItem();
        }
        return null;
    }

    public boolean getItemCooldown(Player player, int slot) {
        Collection<Integer> result = itemCooldown.get(player);
        if (result == null) {
            return false;
        }
        return result.contains(slot);
    }

    public void addCooldown(Player player, int slot) {
        if (itemCooldown.get(player) != null) {
            itemCooldown.get(player).add(slot);
            return;
        }
        Collection<Integer> tempVal1 = new HashSet<>();
        tempVal1.add(slot);
        itemCooldown.put(player, tempVal1);
    }

    public void removeCooldown(Player player, int slot) {
        if (itemCooldown.get(player) != null) {
            itemCooldown.get(player).remove(slot);
        }
    }

    public void clearCooldown() {
        itemCooldown.clear();
    }

}

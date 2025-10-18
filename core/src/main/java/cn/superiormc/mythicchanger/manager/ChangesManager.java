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
        registerNewRule(new AddAttributes());
        registerNewRule(new AddStoredEnchants());
        registerNewRule(new DeleteEnchants());
        registerNewRule(new Empty());
        registerNewRule(new RemoveName());
        registerNewRule(new RemoveAllLore());
        registerNewRule(new RemoveAllEnchants());
        registerNewRule(new RemoveAllStoredEnchants());
        registerNewRule(new RemoveFlags());
        registerNewRule(new RemoveEnchants());
        registerNewRule(new RemoveStoredEnchants());
        registerNewRule(new ReplaceName());
        registerNewRule(new ReplaceItem());
        registerNewRule(new ReplaceLore());
        registerNewRule(new SetCustomModelData());
        registerNewRule(new SetLore());
        registerNewRule(new SetName());
        registerNewRule(new SetType());
        registerNewRule(new SetAmount());
        registerNewRule(new FixHideAttributes());
        registerNewRule(new SetColor());
        registerNewRule(new ParsePAPIName());
        registerNewRule(new ParsePAPILore());
        registerNewRule(new EditItem());
        if (!MythicChanger.freeVersion) {
            if (CommonUtil.checkPluginLoad("NBTAPI")) {
                registerNewRule(new AddNBTString());
                registerNewRule(new AddNBTByte());
                registerNewRule(new AddNBTInt());
                registerNewRule(new AddNBTDouble());
                registerNewRule(new RemoveNBT());
            }
            registerNewRule(new KeepEnchants());
            registerNewRule(new KeepDamage());
            registerNewRule(new KeepName());
            registerNewRule(new KeepLore());
            registerNewRule(new KeepFlags());
            if (CommonUtil.getMinorVersion(20, 5)) {
                registerNewRule(new KeepItemName());
            }
            registerNewRule(new EditLore());
            registerNewRule(new ReplaceEnchants());
            registerNewRule(new ReplaceStoredEnchants());
            registerNewRule(new ReplaceRandomItem());
            registerNewRule(new Deapply());
            registerNewRule(new AddApplyLimit());
            registerNewRule(new SetApplyLimit());
            registerNewRule(new ResetApplyLimit());
            registerNewRule(new RandomChange());
            if (CommonUtil.checkPluginLoad("MMOItems")) {
                registerNewRule(new MIUpdateLore());
            }
            registerNewRule(new SubChange());
        }
        if (CommonUtil.getClass("cn.superiormc.ultimateshop.UltimateShop")) {
            MythicChanger.methodUtil.sendMessage(null, TextUtil.pluginPrefix() + " §fHooking into UltimateShop...");
            registerNewRule(new AddPriceLore());
        }
    }

    public void registerNewRule(AbstractChangesRule rule) {
        rules.add(rule);
        MythicChanger.methodUtil.sendMessage(null, TextUtil.pluginPrefix() + " §fLoaded change rule: " + rule.getClass().getSimpleName() + "!");
    }

    public ItemStack setFakeChange(ConfigurationSection section, ItemStack item, Player player, boolean isPlayerInventory) {
        ObjectSingleChange singleChange = new ObjectSingleChange(section, item, player, true, isPlayerInventory);
        return setFakeChange(singleChange);
    }

    public ItemStack setFakeChange(ConfigurationSection section, ItemStack item, ItemStack original, Player player, boolean isPlayerInventory) {
        ObjectSingleChange singleChange = new ObjectSingleChange(section, item, original, player, true, isPlayerInventory);
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
            ItemStack result = rule.setChange(singleChange);
            if (singleChange.isNeedRewriteItem()) {
                singleChange.replaceItem(result);
            } else {
                singleChange.setItem(result);
            }
        }
        return singleChange.getItem();
    }

    public ItemStack setRealChange(ObjectAction action, ConfigurationSection section, ItemStack item, Player player) {
        ObjectSingleChange singleChange = new ObjectSingleChange(section, item, player, false, true);
        return setRealChange(action, singleChange);
    }

    public ItemStack setRealChange(ObjectAction action, ConfigurationSection section, ItemStack item, ItemStack original, Player player) {
        ObjectSingleChange singleChange = new ObjectSingleChange(section, item, original, player, false, true);
        return setRealChange(action, singleChange);
    }

    public ItemStack setRealChange(ObjectAction action, ObjectSingleChange singleChange) {
        boolean needReturnNewItem = false;
        boolean changedItem = false;
        for (AbstractChangesRule rule : rules) {
            if (rule.configNotContains(singleChange.section)) {
                if (ConfigManager.configManager.getBoolean("debug")) {
                    //Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §c" + rule.getClass().getSimpleName() +  " skipped!");
                }
                continue;
            }
            if (singleChange.getItemMeta() == null || singleChange.getOriginalMeta() == null) {
                break;
            }
            if (ConfigManager.configManager.getBoolean("debug")) {
                Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §fApply real rule: " + rule.getClass().getSimpleName());
            }
            changedItem = true;
            ItemStack result = rule.setChange(singleChange);
            if (singleChange.isNeedRewriteItem()) {
                singleChange.replaceItem(result);
                needReturnNewItem = true;
            }
        }
        if (!MythicChanger.freeVersion && !action.isEmpty() && changedItem) {
            SchedulerUtil.runSync(() -> action.runAllActions(singleChange.getPlayer(), singleChange.getOriginal(), singleChange.getItem()));
        }
        if (needReturnNewItem) {
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

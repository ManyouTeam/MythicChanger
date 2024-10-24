package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.objects.ObjectAction;
import cn.superiormc.mythicchanger.objects.changes.*;
import cn.superiormc.mythicchanger.utils.CommonUtil;
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
        registerNewRule(new FixHideAttributes());
        if (!MythicChanger.freeVersion) {
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fRegistering PREMIUM change rules...");
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
        }
    }

    public void registerNewRule(AbstractChangesRule rule) {
        rules.add(rule);
    }

    public ItemStack setFakeChange(ConfigurationSection section, ItemStack original, ItemStack item, Player player, boolean isPlayerInventory) {
        for (AbstractChangesRule rule : rules) {
            if (rule.configNotContains(section)) {
                continue;
            }
            if (ConfigManager.configManager.getBoolean("debug")) {
                Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fApply fake rule: " + rule.getClass().getName());
            }
            item = rule.setChange(section, item, original, player, true, isPlayerInventory);
        }
        return item;
    }

    public ItemStack setRealChange(ObjectAction action, ConfigurationSection section, ItemStack original, ItemStack item, Player player) {
        boolean needReturnNewItem = false;
        for (AbstractChangesRule rule : rules) {
            if (rule.configNotContains(section)) {
                continue;
            }
            if (ConfigManager.configManager.getBoolean("debug")) {
                Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[MythicChanger] §fApply real rule: " + rule.getClass().getName());
            }
            if (rule.getNeedRewriteItem(section)) {
                item = rule.setChange(section, original, item, player, false, true);
                needReturnNewItem = true;
            } else {
                rule.setChange(section, original, item, player, false, true);
            }
        }
        if (!MythicChanger.freeVersion && !action.isEmpty()) {
            action.runAllActions(player, original, item);
        }
        if (needReturnNewItem) {
            return item;
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

}

package cn.superiormc.mythicchanger.gui;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.listeners.GUIListener;
import cn.superiormc.mythicchanger.methods.Dupe;
import cn.superiormc.mythicchanger.utils.SchedulerUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class InvGUI extends AbstractGUI {

    protected Inventory inv;

    public Listener guiListener;

    public InvGUI(Player player) {
        super(player);
    }

    public abstract boolean clickEventHandle(Inventory inventory, ItemStack item, int slot);

    public void afterClickEventHandle(ItemStack item, ItemStack currentItem, int slot) {
        return;
    }

    public void closeEventHandle(Inventory inventory) {
        return;
    }

    @Override
    public void openGUI() {
        constructGUI();
        if (inv != null) {
            SchedulerUtil.runSync(player, () -> {
                this.guiListener = new GUIListener(this);
                Bukkit.getPluginManager().registerEvents(guiListener, MythicChanger.instance);
                player.openInventory(inv);
            });
        }
    }

    public void setItem(int slot, ItemStack item) {
        inv.setItem(slot, Dupe.markGuiDisplayItem(item));
    }

    public Inventory getInv() {
        return inv;
    }

    public ConfigurationSection getSection() {
        return null;
    }
}

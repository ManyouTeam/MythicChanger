package cn.superiormc.mythicchanger.protolcol.ProtocolLib;

import cn.superiormc.mythicchanger.manager.ChangesManager;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import cn.superiormc.mythicchanger.utils.SchedulerUtil;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetSlot;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetSlots implements PacketListener {

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType().equals(PacketType.Play.Server.SET_SLOT)) {
            WrapperPlayServerSetSlot serverSetSlot = new WrapperPlayServerSetSlot(event);
            Player player = event.getPlayer();
            if (player == null) {
                return;
            }
            if (player.getGameMode() == GameMode.CREATIVE) {
                return;
            }
            int windowID = serverSetSlot.getWindowId();
            ItemStack item = SpigotConversionUtil.toBukkitItemStack(serverSetSlot.getItem());
            if (!ItemUtil.isValid(item)) {
                return;
            }
            int slot = serverSetSlot.getSlot();
            if (ChangesManager.changesManager.getItemCooldown(player, slot)) {
                ChangesManager.changesManager.removeCooldown(player, slot);
            } else {
                ItemStack clientItemStack = ConfigManager.configManager.startFakeChange(item, player,
                        CommonUtil.inPlayerInventory(player, slot, windowID));
                serverSetSlot.setItem(SpigotConversionUtil.fromBukkitItemStack(clientItemStack));
                if (ConfigManager.configManager.getBoolean("real-change-trigger.SetSlotPacket.enabled", true)) {
                    startRealChange(slot, windowID, player);
                }
            }
        }
    }

    public void startRealChange(int slot, int windowID, Player player) {
        if (!CommonUtil.inPlayerInventory(player, slot, windowID)) {
            return;
        }
        int spigotSlot = CommonUtil.convertNMSSlotToBukkitSlot(slot, windowID, player);
        ItemStack tempItemStack = CommonUtil.getItemFromSlot(player, spigotSlot);
        if (tempItemStack == null || tempItemStack.getType().isAir()) {
            return;
        }
        ChangesManager.changesManager.addCooldown(player, slot);
        ItemStack newItem = ConfigManager.configManager.startRealChange(tempItemStack, player);
        if (ItemUtil.isValid(newItem)) {
            SchedulerUtil.runSync(() -> player.getInventory().setItem(spigotSlot, newItem));
        }
    }
}

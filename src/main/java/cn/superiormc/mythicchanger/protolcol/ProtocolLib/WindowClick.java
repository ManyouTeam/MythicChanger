package cn.superiormc.mythicchanger.protolcol.ProtocolLib;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WindowClick extends GeneralPackets{

    // 客户端发给服务端
    public WindowClick() {
        super();
    }

    @Override
    protected void initPacketAdapter() {
        packetAdapter = new PacketAdapter(MythicChanger.instance, ListenerPriority.NORMAL, PacketType.Play.Client.WINDOW_CLICK) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                if (player == null) {
                    return;
                }
                if (player.getGameMode() == GameMode.CREATIVE) {
                    return;
                }
                PacketContainer packet = event.getPacket();
                int windowID = packet.getIntegers().read(0);
                int slot = packet.getIntegers().read(2);
                if (windowID == 0) {
                    if (slot < 5 || slot > 44) {
                        return;
                    }
                    int spigotSlot;
                    if (slot >= 36) {
                        spigotSlot = slot - 36;
                    } else if (slot <= 8) {
                        spigotSlot = slot + 31;
                    } else {
                        spigotSlot = slot;
                    }
                    ItemStack tempItemStack = event.getPlayer().getInventory().getItem(spigotSlot);
                    if (tempItemStack == null || tempItemStack.getType().isAir()) {
                        return;
                    }
                    ItemStack newItem = ConfigManager.configManager.startRealChange(tempItemStack, player);
                    if (newItem != null) {
                        Bukkit.getScheduler().runTask(MythicChanger.instance, () -> {
                            if (!player.getItemOnCursor().getType().isAir()) {
                                player.getItemOnCursor().setAmount(0);
                            }
                            player.getInventory().setItem(spigotSlot, newItem);
                        });
                    }
                } else {
                    if (slot < 10 || slot > 44) {
                        return;
                    }
                    int spigotSlot;
                    if (slot >= 36) {
                        spigotSlot = slot - 36;
                    } else {
                        spigotSlot = slot;
                    }
                    ItemStack tempItemStack = event.getPlayer().getInventory().getItem(spigotSlot);
                    if (tempItemStack == null || tempItemStack.getType().isAir()) {
                        return;
                    }
                    ItemStack newItem = ConfigManager.configManager.startRealChange(tempItemStack, player);
                    if (newItem != null && !newItem.getType().isAir()) {
                        Bukkit.getScheduler().runTask(MythicChanger.instance, () -> {
                            if (!player.getItemOnCursor().getType().isAir()) {
                                player.getItemOnCursor().setAmount(0);
                            }
                            player.getInventory().setItem(spigotSlot, newItem);
                        });
                    }
                }
            }
        };
    }
}
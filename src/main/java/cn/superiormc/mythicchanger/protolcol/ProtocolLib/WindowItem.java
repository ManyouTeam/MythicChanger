package cn.superiormc.mythicchanger.protolcol.ProtocolLib;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

// 服务端发给客户端
public class WindowItem extends GeneralPackets {

    public WindowItem() {
        super();
    }

    @Override
    protected void initPacketAdapter() {
        packetAdapter = new PacketAdapter(MythicChanger.instance, ListenerPriority.LOWEST, PacketType.Play.Server.WINDOW_ITEMS) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if (event.getPlayer() == null) {
                    return;
                }
                if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
                    return;
                }
                PacketContainer packet = event.getPacket();

                StructureModifier<ItemStack> singleItemStackStructureModifier = packet.getItemModifier();
                if (singleItemStackStructureModifier.size() != 0) {
                    ItemStack serverItemStack = singleItemStackStructureModifier.read(0);
                    ItemStack clientItemStack = ConfigManager.configManager.startFakeChange(serverItemStack, event.getPlayer(), true);
                    // client 是加过 Lore 的，server 是没加过的！
                    singleItemStackStructureModifier.write(0, clientItemStack);
                }
                StructureModifier<List<ItemStack>> itemStackStructureModifier = packet.getItemListModifier();
                List<ItemStack> serverItemStack = itemStackStructureModifier.read(0);
                List<ItemStack> clientItemStack = new ArrayList<>();
                int index = 1;
                for (ItemStack itemStack : serverItemStack) {
                    if (itemStack.getType().isAir()) {
                        clientItemStack.add(itemStack);
                        index ++;
                        continue;
                    }
                    boolean isPlayerInventory = event.getPacket().getIntegers().read(0) == 0 || index > serverItemStack.size() - 36;
                    clientItemStack.add(ConfigManager.configManager.startFakeChange(itemStack,
                            event.getPlayer(), isPlayerInventory
                            ));
                    index ++;
                }
                // client 是加过 Lore 的，server 是没加过的！
                itemStackStructureModifier.write(0, clientItemStack);
            }
        };
    }
}

package cn.superiormc.mythicchanger.protolcol.ProtocolLib;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SetSlots extends GeneralPackets {

    public Map<Player, Collection<ItemStack>> playerMap = new HashMap<>();

    public SetSlots() {
        super();
    }
    @Override
    protected void initPacketAdapter(){
        packetAdapter = new PacketAdapter(MythicChanger.instance, ListenerPriority.LOWEST, PacketType.Play.Server.SET_SLOT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                Player player = event.getPlayer();
                if (player == null) {
                    return;
                }
                PacketContainer packet = event.getPacket();
                StructureModifier<ItemStack> itemStackStructureModifier = packet.getItemModifier();
                ItemStack serverItemStack = itemStackStructureModifier.read(0);
                if (serverItemStack == null || serverItemStack.getType().isAir()) {
                    return;
                }
                int slot = packet.getIntegers().read(packet.getIntegers().size() - 1);
                ItemStack clientItemStack = ConfigManager.configManager.startFakeChange(serverItemStack, player);
                // client 是加过 Lore 的，server 是没加过的！
                itemStackStructureModifier.write(0, clientItemStack);
                if (slot < 5 || slot > 44) {
                    return;
                }
                int spigotSlot = slot;
                if (slot >= 36) {
                    spigotSlot = slot - 36;
                } else if (slot <= 8) {
                    spigotSlot = slot + 31;
                }
                ItemStack tempItemStack = event.getPlayer().getInventory().getItem(spigotSlot);
                if (tempItemStack == null || tempItemStack.getType().isAir()) {
                    return;
                }
                ConfigManager.configManager.startRealChange(tempItemStack, player);
            }
        };
    }
}

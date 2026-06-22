package cn.superiormc.mythicchanger.protolcol.pacetevents;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.Equipment;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEquipment;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EntityEquipment implements PacketListener {

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (!event.getPacketType().equals(PacketType.Play.Server.ENTITY_EQUIPMENT)) {
            return;
        }
        if (!ConfigManager.configManager.getBoolean("fake-change-trigger.EntityEquipmentPacket.enabled", false)) {
            return;
        }
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }
        if (ConfigManager.configManager.getBoolean("debug")) {
            TextUtil.sendMessage(null, TextUtil.pluginPrefix() + " §fEntityEquipment pack found!");
        }
        WrapperPlayServerEntityEquipment entityEquipment = new WrapperPlayServerEntityEquipment(event);
        List<Equipment> clientEquipment = new ArrayList<>();
        for (Equipment equipment : entityEquipment.getEquipment()) {
            ItemStack item = SpigotConversionUtil.toBukkitItemStack(equipment.getItem());
            if (!ItemUtil.isValid(item)) {
                clientEquipment.add(equipment);
                continue;
            }
            ItemStack clientItemStack = ConfigManager.configManager.startFakeChange(item, player, false);
            clientEquipment.add(new Equipment(equipment.getSlot(), SpigotConversionUtil.fromBukkitItemStack(clientItemStack)));
        }
        entityEquipment.setEquipment(clientEquipment);
    }
}

package cn.superiormc.mythicchanger.protolcol.ProtocolLib;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetCursorItem;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetCursorItem implements PacketListener {

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (!event.getPacketType().equals(PacketType.Play.Server.SET_CURSOR_ITEM)) {
            return;
        }
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }
        WrapperPlayServerSetCursorItem serverSetSlot = new WrapperPlayServerSetCursorItem(event);
        ItemStack item = SpigotConversionUtil.toBukkitItemStack(serverSetSlot.getStack());
        if (!ItemUtil.isValid(item)) {
            return;
        }
        serverSetSlot.setStack(SpigotConversionUtil.fromBukkitItemStack(ConfigManager.configManager.startFakeChange(item, player, true)));
    }
}

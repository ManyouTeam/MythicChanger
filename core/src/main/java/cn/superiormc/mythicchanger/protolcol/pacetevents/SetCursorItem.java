package cn.superiormc.mythicchanger.protolcol.pacetevents;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.item.HashedStack;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetCursorItem;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SetCursorItem implements PacketListener {

    public static Map<Player, HashedStack> hashedStackMap = new HashMap<>();

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
        com.github.retrooper.packetevents.protocol.item.ItemStack original = serverSetSlot.getStack();
        ItemStack item = SpigotConversionUtil.toBukkitItemStack(original);
        if (!ItemUtil.isValid(item)) {
            return;
        }
        serverSetSlot.setStack(SpigotConversionUtil.fromBukkitItemStack(ConfigManager.configManager.startFakeChange(item, player, true)));
        hashedStackMap.put(player, HashedStack.fromItemStack(original));
    }
}

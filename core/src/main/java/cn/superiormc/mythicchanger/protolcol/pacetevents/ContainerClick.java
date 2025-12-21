package cn.superiormc.mythicchanger.protolcol.pacetevents;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import org.bukkit.entity.Player;

public class ContainerClick implements PacketListener {

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (!event.getPacketType().equals(PacketType.Play.Client.CLICK_WINDOW)) {
            return;
        }

        WrapperPlayClientClickWindow wrapper = new WrapperPlayClientClickWindow(event);
        Player player = event.getPlayer();

        if (SetCursorItem.hashedStackMap.containsKey(player)) {
            wrapper.setCarriedHashedStack(SetCursorItem.hashedStackMap.get(player));
        }
    }
}

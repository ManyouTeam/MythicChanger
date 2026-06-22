package cn.superiormc.mythicchanger.protolcol.pacetevents;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Optional;

public class EntityMetadata implements PacketListener {

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (!event.getPacketType().equals(PacketType.Play.Server.ENTITY_METADATA)) {
            return;
        }
        if (!ConfigManager.configManager.getBoolean("fake-change-trigger.EntityMetadataPacket.enabled", true)) {
            return;
        }
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }
        WrapperPlayServerEntityMetadata entityMetadata = new WrapperPlayServerEntityMetadata(event);
        for (EntityData<?> entityData : entityMetadata.getEntityMetadata()) {
            if (entityData.getType() == EntityDataTypes.ITEMSTACK) {
                sendDebugMessage();
                setItemStack(entityData, player);
            } else if (entityData.getType() == EntityDataTypes.OPTIONAL_ITEMSTACK) {
                sendDebugMessage();
                setOptionalItemStack(entityData, player);
            }
        }
    }

    private void sendDebugMessage() {
        if (ConfigManager.configManager.getBoolean("debug")) {
            TextUtil.sendMessage(null, TextUtil.pluginPrefix() + " §fEntityMetadata pack found!");
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setItemStack(EntityData entityData, Player player) {
        Object value = entityData.getValue();
        if (!(value instanceof ItemStack itemStack) || itemStack.isEmpty()) {
            return;
        }
        org.bukkit.inventory.ItemStack bukkitItem = SpigotConversionUtil.toBukkitItemStack(itemStack);
        if (!ItemUtil.isValid(bukkitItem)) {
            return;
        }
        org.bukkit.inventory.ItemStack clientItemStack = ConfigManager.configManager.startFakeChange(bukkitItem, player, false);
        entityData.setValue(SpigotConversionUtil.fromBukkitItemStack(clientItemStack));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setOptionalItemStack(EntityData entityData, Player player) {
        Object value = entityData.getValue();
        if (!(value instanceof Optional<?> optional) || optional.isEmpty() || !(optional.get() instanceof ItemStack itemStack) || itemStack.isEmpty()) {
            return;
        }
        org.bukkit.inventory.ItemStack bukkitItem = SpigotConversionUtil.toBukkitItemStack(itemStack);
        if (!ItemUtil.isValid(bukkitItem)) {
            return;
        }
        org.bukkit.inventory.ItemStack clientItemStack = ConfigManager.configManager.startFakeChange(bukkitItem, player, false);
        entityData.setValue(Optional.of(SpigotConversionUtil.fromBukkitItemStack(clientItemStack)));
    }
}

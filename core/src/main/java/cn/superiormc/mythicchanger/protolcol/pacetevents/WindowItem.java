package cn.superiormc.mythicchanger.protolcol.pacetevents;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowItems;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 服务端发给客户端
public class WindowItem implements PacketListener {

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType().equals(PacketType.Play.Server.WINDOW_ITEMS)) {
            Player player = event.getPlayer();
            if (player == null) {
                return;
            }
            if (player.getGameMode() == GameMode.CREATIVE) {
                return;
            }
            if (ConfigManager.configManager.getBoolean("debug")) {
                TextUtil.sendMessage(null, TextUtil.pluginPrefix() + " §fWindowItems pack found!");
            }
            WrapperPlayServerWindowItems windowItems = new WrapperPlayServerWindowItems(event);
            Optional<com.github.retrooper.packetevents.protocol.item.ItemStack> optionalCarriedItem = windowItems.getCarriedItem();
            if (optionalCarriedItem.isPresent()) {
                ItemStack carriedItem = SpigotConversionUtil.toBukkitItemStack(optionalCarriedItem.get());
                if (ItemUtil.isValid(carriedItem)) {
                    ItemStack clientItemStack = ConfigManager.configManager.startFakeChange(carriedItem, event.getPlayer(), true);
                    windowItems.setCarriedItem(SpigotConversionUtil.fromBukkitItemStack(clientItemStack));
                }
            }
            List<com.github.retrooper.packetevents.protocol.item.ItemStack> tempItems = windowItems.getItems();
            List<com.github.retrooper.packetevents.protocol.item.ItemStack> clientItemStack = new ArrayList<>();
            int index = 1;
            for (com.github.retrooper.packetevents.protocol.item.ItemStack item : tempItems) {
                if (item.isEmpty()) {
                    clientItemStack.add(item);
                    index ++;
                    continue;
                }
                boolean isPlayerInventory = windowItems.getWindowId() == 0 || index > windowItems.getItems().size() - 36;
                clientItemStack.add(SpigotConversionUtil.fromBukkitItemStack(ConfigManager.configManager.startFakeChange(SpigotConversionUtil.toBukkitItemStack(item),
                        event.getPlayer(), isPlayerInventory
                )));
                index ++;
            }
            windowItems.setItems(clientItemStack);
        }
    }
}

package cn.superiormc.mythicchanger.protolcol.ProtocolLib;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class WindowMerchant extends GeneralPackets {

    public WindowMerchant() {
        super();
    }

    @Override
    protected void initPacketAdapter() {
        packetAdapter = new PacketAdapter(MythicChanger.instance, ListenerPriority.LOWEST, PacketType.Play.Server.OPEN_WINDOW_MERCHANT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                List<MerchantRecipe> list = new ArrayList<>();
                packet.getMerchantRecipeLists().read(0).forEach(recipe -> {
                    ItemStack serverItemStack1 = recipe.getResult();
                    List<ItemStack> serverItemStack2List = recipe.getIngredients();
                    MerchantRecipe merchantRecipe = new MerchantRecipe(ConfigManager.configManager.startFakeChange(
                            serverItemStack1, event.getPlayer(), false),
                            recipe.getUses(), recipe.getMaxUses(),
                            recipe.hasExperienceReward(),
                            recipe.getVillagerExperience(),
                            recipe.getPriceMultiplier(),
                            recipe.getDemand(),
                            recipe.getSpecialPrice());
                    for (ItemStack serverItemStack2 : serverItemStack2List) {
                        merchantRecipe.addIngredient(ConfigManager.configManager.startFakeChange(
                                serverItemStack2,
                                event.getPlayer(),
                                false));
                    }
                    list.add(merchantRecipe);
                });

                packet.getMerchantRecipeLists().write(0, list);
            }
        };
    }
}

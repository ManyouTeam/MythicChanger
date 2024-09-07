package cn.superiormc.mythicchanger.commands;

import cn.superiormc.mythicchanger.manager.LanguageManager;
import cn.superiormc.mythicchanger.objects.ObjectCommand;
import de.tr7zw.nbtapi.*;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class SubViewNBT extends ObjectCommand {


    public SubViewNBT() {
        this.id = "viewnbt";
        this.requiredPermission =  "mythicchanger." + id;
        this.onlyInGame = true;
        this.requiredArgLength = new Integer[]{1};
    }

    @Override
    public void executeCommandInGame(String[] args, Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().isAir()) {
            LanguageManager.languageManager.sendStringText(player, "error.item-empty");
            return;
        }
        NBTItem nbtItem = new NBTItem(item);
        if (!nbtItem.hasCustomNbtData()) {
            LanguageManager.languageManager.sendStringText(player, "nbt-view-empty");
            return;
        }
        LanguageManager.languageManager.sendStringText(player, "nbt-view");
        parseNBT(player, nbtItem, 0);
    }

    private void parseNBT(Player player, ReadWriteNBT nbtCompound, int b) {
        Set<String> nbts = nbtCompound.getKeys();
        for (String key : nbts) {
            String[] keyC = key.split("\\.");
            int c = keyC.length - 1 + b;
            if (nbtCompound.getCompound(key) != null || nbtCompound.hasTag(key, NBTType.NBTTagCompound)) {
                StringBuilder display = new StringBuilder("  ");
                for (int i = 0 ; i < c ; i ++) {
                    display.append("§f    ");
                }
                display.append("§6(Compound) §f").append(keyC[keyC.length - 1]).append("§f:");
                player.sendMessage(display.toString());
                parseNBT(player, nbtCompound.getCompound(key), b + 1);
            } else {
                //Bukkit.getConsoleSender().sendMessage(key + "  " +  nbtCompound.getType(key));
                //if (nbtCompound.hasTag(key, NBTType.NBTTagList)) {
                //    if (nbtCompound.getCompoundList(key) != null) {
                //        StringBuilder display = new StringBuilder("  ");
                //        for (int i = 0 ; i < c ; i ++) {
                //            display.append("§f    ");
                //        }
                //        display.append("§6(Compound List) §f").append(keyC[keyC.length - 1]).append("§f:");
                //        player.sendMessage(display.toString());
                //        for (ReadWriteNBT tempVal1 : nbtCompound.getCompoundList(key)) {
                //            parseNBT(player, tempVal1, b + 1);
                //        }
                //    }
                //} else
                    if (nbtCompound.hasTag(key, NBTType.NBTTagByte)) {
                    StringBuilder display = new StringBuilder("  ");
                    for (int i = 0 ; i < c ; i ++) {
                        display.append("§f    ");
                    }
                    display.append("§6(Byte) §f").append(keyC[keyC.length - 1]).append("§f: ").append(nbtCompound.getByte(key));
                    player.sendMessage(display.toString());
                } else if (nbtCompound.hasTag(key, NBTType.NBTTagShort)) {
                    StringBuilder display = new StringBuilder("  ");
                    for (int i = 0 ; i < c ; i ++) {
                        display.append("§f    ");
                    }
                    display.append("§6(Short) §f").append(keyC[keyC.length - 1]).append("§f: ").append(nbtCompound.getShort(key));
                    player.sendMessage(display.toString());
                } else if (nbtCompound.hasTag(key, NBTType.NBTTagInt)) {
                    StringBuilder display = new StringBuilder("  ");
                    for (int i = 0 ; i < c ; i ++) {
                        display.append("§f    ");
                    }
                    display.append("§6(Int) §f").append(keyC[keyC.length - 1]).append("§f: ").append(nbtCompound.getInteger(key));
                    player.sendMessage(display.toString());
                } else if (nbtCompound.hasTag(key, NBTType.NBTTagLong)) {
                    StringBuilder display = new StringBuilder("  ");
                    for (int i = 0 ; i < c ; i ++) {
                        display.append("§f    ");
                    }
                    display.append("§6(Long) §f").append(keyC[keyC.length - 1]).append("§f: ").append(nbtCompound.getLong(key));
                    player.sendMessage(display.toString());
                } else if (nbtCompound.hasTag(key, NBTType.NBTTagFloat)) {
                    StringBuilder display = new StringBuilder("  ");
                    for (int i = 0 ; i < c ; i ++) {
                        display.append("§f    ");
                    }
                    display.append("§6(Float) §f").append(keyC[keyC.length - 1]).append("§f: ").append(nbtCompound.getFloat(key));
                    player.sendMessage(display.toString());
                } else if (nbtCompound.hasTag(key, NBTType.NBTTagDouble)) {
                    StringBuilder display = new StringBuilder("  ");
                    for (int i = 0 ; i < c ; i ++) {
                        display.append("§f    ");
                    }
                    display.append("§6(Double) §f").append(keyC[keyC.length - 1]).append("§f: ").append(nbtCompound.getDouble(key));
                    player.sendMessage(display.toString());
                } else if (nbtCompound.hasTag(key, NBTType.NBTTagEnd)) {
                    StringBuilder display = new StringBuilder("  ");
                    for (int i = 0 ; i < c ; i ++) {
                        display.append("§f    ");
                    }
                    display.append("§6(End) §f").append(keyC[keyC.length - 1]);
                    player.sendMessage(display.toString());
                } else if (nbtCompound.hasTag(key, NBTType.NBTTagString)) {
                    StringBuilder display = new StringBuilder("  ");
                    for (int i = 0 ; i < c ; i ++) {
                        display.append("§f    ");
                    }
                    display.append("§6(String) §f").append(keyC[keyC.length - 1]).append("§f: ").append(nbtCompound.getString(key));
                    player.sendMessage(display.toString());
                }
            }
        }
    }
}

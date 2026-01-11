package cn.superiormc.mythicchanger.objects;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.methods.DebuildItem;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import cn.superiormc.mythicchanger.utils.NBTUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractSingleRun {

    protected ConfigurationSection section;

    public AbstractSingleRun(ConfigurationSection section) {
        this.section = section;
    }

    protected String replacePlaceholder(String content, Player player, ItemStack original, ItemStack item) {
        content = CommonUtil.modifyString(player, content
                ,"world", player.getWorld().getName()
                ,"original-name", ItemUtil.getItemName(original)
                ,"name", ItemUtil.getItemName(item)
                ,"amount", String.valueOf(item.getAmount())
                ,"max-stack", String.valueOf(item.getMaxStackSize())
                ,"player_x", String.valueOf(player.getLocation().getX())
                ,"player_y", String.valueOf(player.getLocation().getY())
                ,"player_z", String.valueOf(player.getLocation().getZ())
                ,"player_pitch", String.valueOf(player.getLocation().getPitch())
                ,"player_yaw", String.valueOf(player.getLocation().getYaw())
                ,"player", player.getName());
        ConfigurationSection debuildItemFormat = null;
        if (!MythicChanger.freeVersion) {
            Pattern pattern1 = Pattern.compile("\\{item_(.*?)}");
            Matcher matcher1 = pattern1.matcher(content);
            while (matcher1.find()) {
                if (debuildItemFormat == null) {
                    debuildItemFormat = DebuildItem.debuildItem(item, new MemoryConfiguration());
                }
                String key = matcher1.group(1);
                String defaultValue = "";
                String[] tempVal1 = key.split(";;");
                if (tempVal1.length >= 2) {
                    defaultValue = tempVal1[1];
                }
                Object value = debuildItemFormat.get(tempVal1[0]);
                if (value == null) {
                    value = defaultValue;
                }
                content = content.replace("{item_" + key + "}", value.toString());
            }
            if (CommonUtil.checkPluginLoad("NBTAPI")) {
                Pattern pattern2 = Pattern.compile("\\{nbt_(.*?)}");
                Matcher matcher2 = pattern2.matcher(content);
                while (matcher2.find()) {
                    String key = matcher2.group(1);
                    String defaultValue = "";
                    String[] tempVal1 = key.split(";;");
                    if (tempVal1.length >= 2) {
                        defaultValue = tempVal1[tempVal1.length - 1];
                    }
                    Object value = NBTUtil.parseNBT(item, tempVal1[0]);
                    if (value == null) {
                        value = defaultValue;
                    }
                    content = content.replace("{nbt_" + key + "}", value.toString());
                }
            }
        }
        content = TextUtil.withPAPI(content, player);
        return content;
    }

    public String getString(String path) {
        return section.getString(path);
    }

    public List<String> getStringList(String path) {
        return section.getStringList(path);
    }

    public int getInt(String path) {
        return section.getInt(path);
    }

    public int getInt(String path, int defaultValue) {
        return section.getInt(path, defaultValue);
    }

    public double getDouble(String path) {
        return section.getDouble(path);
    }

    public double getDouble(String path, Player player, ItemStack original, ItemStack item) {
        return Double.parseDouble(replacePlaceholder(section.getString(path), player, original, item));
    }

    public boolean getBoolean(String path, boolean defaultValue) {
        return section.getBoolean(path, defaultValue);
    }

    public String getString(String path, Player player, ItemStack original, ItemStack item) {
        return replacePlaceholder(section.getString(path), player, original, item);
    }

    public ConfigurationSection getSection() {
        return section;
    }
}

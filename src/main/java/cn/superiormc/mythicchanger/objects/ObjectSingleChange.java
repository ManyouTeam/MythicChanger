package cn.superiormc.mythicchanger.objects;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.methods.DebuildItem;
import cn.superiormc.mythicchanger.utils.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectSingleChange extends MemoryConfiguration {

    public ConfigurationSection section;

    private final ItemStack original;

    private ItemStack item;

    private final Player player;

    private final boolean fakeOrReal;

    private final boolean isPlayerInventory;

    private ItemMeta itemMeta;

    private final ItemMeta originalMeta;

    private final ConfigurationSection debuildItemFormat;

    private final String itemName;

    private final String originalName;

    public ObjectSingleChange(ConfigurationSection section,
                              ItemStack item,
                              Player player,
                              boolean fakeOrReal,
                              boolean isPlayerInventory) {
        this.section = section;
        this.original = item.clone();
        this.item = item;
        this.itemMeta = item.getItemMeta();
        this.originalMeta = original.getItemMeta();
        this.debuildItemFormat = DebuildItem.debuildItem(item, new MemoryConfiguration());
        this.player = player;
        this.fakeOrReal = fakeOrReal;
        this.isPlayerInventory = isPlayerInventory;
        this.itemName = ItemUtil.getItemName(item);
        this.originalName = ItemUtil.getItemName(original);
    }

    public ObjectSingleChange(ConfigurationSection section,
                              ObjectSingleChange change) {
        this.section = section;
        this.original = change.getOriginal();
        this.item = change.getItem();
        this.itemMeta = change.getItemMeta();
        this.originalMeta = change.getOriginalMeta();
        this.debuildItemFormat = change.getDebuildItemFormat();
        this.player = change.getPlayer();
        this.fakeOrReal = change.isFakeOrReal();
        this.isPlayerInventory = change.isPlayerInventory();
        this.itemName = change.getItemName();
        this.originalName = change.getOriginalName();
    }

    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    public ItemMeta getOriginalMeta() {
        return originalMeta;
    }

    public ItemStack setItemMeta(ItemMeta meta) {
        this.itemMeta = meta;
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemStack getOriginal() {
        return original;
    }

    public void replaceItem(ItemStack item) {
        if (item == null) {
            return;
        }
        this.item = item;
        this.itemMeta = item.getItemMeta();
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public ConfigurationSection getDebuildItemFormat() {
        return debuildItemFormat;
    }

    public String getItemName() {
        return itemName;
    }

    public String getOriginalName() {
        return originalName;
    }

    @Override
    public int getInt(@NotNull String path) {
        Object value = section.get(path);
        if (value == null) {
            return 0;
        }
        if (value instanceof Integer) {
            return section.getInt(path);
        }
        return MathUtil.doCalculate(TextUtil.withPAPI(parsePlaceholder(value.toString()), player)).intValue();
    }

    @Override
    public int getInt(@NotNull String path, int defaultValue) {
        Object value = section.get(path);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Integer) {
            return section.getInt(path);
        }
        return MathUtil.doCalculate(TextUtil.withPAPI(parsePlaceholder(value.toString()), player)).intValue();
    }

    @Override
    public double getDouble(@NotNull String path) {
        if (section.isDouble(path)) {
            return section.getDouble(path);
        }
        return MathUtil.doCalculate(TextUtil.withPAPI(parsePlaceholder(section.getString(path)), player)).doubleValue();
    }

    @Override
    public double getDouble(@NotNull String path, double defaultValue) {
        if (section.isDouble(path)) {
            return section.getDouble(path);
        }
        return MathUtil.doCalculate(TextUtil.withPAPI(parsePlaceholder(section.getString(path)), player)).doubleValue();
    }

    @Override
    public String getString(@NotNull String path) {
        String value = section.getString(path);
        if (value == null) {
            return null;
        }
        return TextUtil.parse(parsePlaceholder(value), player);
    }

    @Override
    public String getString(@NotNull String path, String defaultValue) {
        String value = section.getString(path, defaultValue);
        if (value == null) {
            return null;
        }
        return TextUtil.parse(parsePlaceholder(value), player);
    }

    @Override
    public @NotNull List<String> getStringList(@NotNull String path) {
        return TextUtil.getListWithColorAndPAPI(parsePlaceholder(section.getStringList(path)), player);
    }

    @Override
    public @Nullable ObjectSingleChange getConfigurationSection(@NotNull String path) {
        if (section.getConfigurationSection(path) == null) {
            return null;
        }
        return new ObjectSingleChange(section.getConfigurationSection(path), this);
    }

    @Override
    public boolean getBoolean(@NotNull String path) {
        return section.getBoolean(path);
    }

    @Override
    public @Nullable Object get(@NotNull String path) {
        return section.getBoolean(path);
    }

    @Override
    public boolean contains(@NotNull String path) {
        return section.contains(path);
    }

    @Override
    public @NotNull Set<String> getKeys(boolean deep) {
        return section.getKeys(deep);
    }

    public String parsePlaceholder(String text) {
        text = CommonUtil.modifyString(text, "amount", String.valueOf(item.getAmount()),
                "max-stack", String.valueOf(item.getMaxStackSize()),
                "name", itemName,
                "original-name", originalName);
        if (!MythicChanger.freeVersion) {
            Pattern pattern1 = Pattern.compile("\\{item_(.*?)}");
            Matcher matcher1 = pattern1.matcher(text);
            while (matcher1.find()) {
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
                text = text.replace("{item_" + key + "}", value.toString());
            }
            if (CommonUtil.checkPluginLoad("NBTAPI")) {
                Pattern pattern2 = Pattern.compile("\\{nbt_(.*?)}");
                Matcher matcher2 = pattern2.matcher(text);
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
                    text = text.replace("{nbt_" + key + "}", value.toString());
                }
            }
        }
        return text;
    }

    public List<String> parsePlaceholder(List<String> text) {
        List<String> result = new ArrayList<>();
        for (String singleText : text) {
            result.add(parsePlaceholder(singleText));
        }
        return result;
    }

    public boolean isFakeOrReal() {
        return fakeOrReal;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isPlayerInventory() {
        return isPlayerInventory;
    }
}

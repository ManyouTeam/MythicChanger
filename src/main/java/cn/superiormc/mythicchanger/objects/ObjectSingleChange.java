package cn.superiormc.mythicchanger.objects;

import cn.superiormc.mythicchanger.methods.DebuildItem;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import cn.superiormc.mythicchanger.utils.MathUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectSingleChange {

    public ConfigurationSection section;

    private final ItemStack original;

    private final ItemStack item;

    private final Player player;

    private final boolean fakeOrReal;

    private final boolean isPlayerInventory;

    private ItemMeta itemMeta;

    private final ItemMeta originalMeta;

    private final ConfigurationSection debuildItemFormat;

    private final String itemName;

    private final String originalName;

    public ObjectSingleChange(ConfigurationSection section,
                              ItemStack original,
                              ItemStack item,
                              Player player,
                              boolean fakeOrReal,
                              boolean isPlayerInventory) {
        this.section = section;
        this.original = original;
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

    public int getInt(String path) {
        Object value = section.get(path);
        if (value == null) {
            return 0;
        }
        if (value instanceof Integer) {
            return section.getInt(path);
        }
        return MathUtil.doCalculate(TextUtil.withPAPI(parsePlaceholder(value.toString()), player)).intValue();
    }

    public String getString(String path, String... args) {
        return TextUtil.parse(CommonUtil.modifyString(parsePlaceholder(section.getString(path, "")), args), player);
    }

    public List<String> getStringList(String path) {
        return TextUtil.getListWithColorAndPAPI(section.getStringList(path), player);
    }

    public boolean getBoolean(String path) {
        return section.getBoolean(path);
    }

    public ConfigurationSection getItemFormatSection(String path) {
        ConfigurationSection result = new MemoryConfiguration();
        ConfigurationSection tempVal1 = section.getConfigurationSection(path);
        if (tempVal1 == null) {
            return new MemoryConfiguration();
        }
        for (String key : tempVal1.getKeys(true)) {
            List<String> listValue = tempVal1.getStringList(key);
            if (!listValue.isEmpty()) {
                result.set(key, TextUtil.getListWithColorAndPAPI(section.getStringList(path), player));
            } else {
                Object value = tempVal1.get(key);
                if (value instanceof Number) {
                    result.set(key, MathUtil.doCalculate(TextUtil.withPAPI(parsePlaceholder(value.toString()), player)).toString());
                } else {
                    result.set(key, TextUtil.parse(parsePlaceholder(section.getString(path, "")), player));
                }
            }
        }
        return result;
    }

    private String parsePlaceholder(String text) {
        text = CommonUtil.modifyString(text, "amount", String.valueOf(item.getAmount()),
                "max-stack", String.valueOf(item.getMaxStackSize()),
                "name", itemName,
                "original-name", originalName);
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
        return text;
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

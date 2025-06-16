package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class SetColor extends AbstractChangesRule {

    public SetColor() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ItemMeta meta = singleChange.getItemMeta();
        if (meta instanceof LeatherArmorMeta) {
            LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
            armorMeta.setColor(Color.fromRGB(singleChange.getInt("set-color")));
            return singleChange.setItemMeta(armorMeta);
        }
        return singleChange.getItem();
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("set-color", 150);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("set-color");
    }
}

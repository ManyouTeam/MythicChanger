package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class SetAmount extends AbstractChangesRule {

    public SetAmount() {
        super();
    }

    @Override
    public ItemStack setChange(ConfigurationSection section,
                               ItemStack original,
                               ItemStack item,
                               Player player,
                               boolean fakeOrReal,
                               boolean isPlayerInventory) {
        item.setAmount(Integer.parseInt(TextUtil.withPAPI(CommonUtil.modifyString(section.getString("set-amount"),
                "amount", String.valueOf(item.getAmount()),
                "max-stack", String.valueOf(item.getMaxStackSize())), player)));
        return item;
    }

    @Override
    public int getWeight() {
        return ConfigManager.configManager.getRuleWeight("set-amount", 59);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getString("set-amount") == null;
    }
}

package cn.superiormc.mythicchanger.objects.conditions;

import cn.superiormc.mythicchanger.manager.ErrorManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractCheckCondition {

    private final String type;

    private String[] requiredArgs;

    public AbstractCheckCondition(String type) {
        this.type = type;
    }

    protected void setRequiredArgs(String... requiredArgs) {
        this.requiredArgs = requiredArgs;
    }

    public boolean checkCondition(ObjectSingleCondition singleCondition, Player player, ItemStack original, ItemStack item) {
        if (requiredArgs != null) {
            for (String arg : requiredArgs) {
                if (!singleCondition.getSection().contains(arg)) {
                    ErrorManager.errorManager.sendErrorMessage("§x§9§8§F§B§9§8[MythicChanger] §cError: Your condition missing required arg: " + arg + ".");
                    return true;
                }
            }
        }
        return onCheckCondition(singleCondition, player, original, item);
    }

    protected abstract boolean onCheckCondition(ObjectSingleCondition singleCondition, Player player, ItemStack original, ItemStack item);

    public String getType() {
        return type;
    }
}

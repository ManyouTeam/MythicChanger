package cn.superiormc.mythicchanger.objects.actions;


import cn.superiormc.mythicchanger.manager.ErrorManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractRunAction {

    private final String type;

    private String[] requiredArgs;

    public AbstractRunAction(String type) {
        this.type = type;
    }

    protected void setRequiredArgs(String... requiredArgs) {
        this.requiredArgs = requiredArgs;
    }

    public void runAction(ObjectSingleAction singleAction, Player player, ItemStack original, ItemStack item) {
        if (requiredArgs != null) {
            for (String arg : requiredArgs) {
                if (!singleAction.getSection().contains(arg)) {
                    ErrorManager.errorManager.sendErrorMessage("§cError: Your action missing required arg: " + arg + ".");
                    return;
                }
            }
        }
        onDoAction(singleAction, player, original, item);
    }

    protected abstract void onDoAction(ObjectSingleAction singleAction, Player player, ItemStack original, ItemStack item);

    public String getType() {
        return type;
    }
}

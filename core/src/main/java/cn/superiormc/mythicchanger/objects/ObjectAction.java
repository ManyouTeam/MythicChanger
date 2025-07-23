package cn.superiormc.mythicchanger.objects;

import cn.superiormc.mythicchanger.objects.actions.ObjectSingleAction;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectAction {

    private ConfigurationSection section;

    private final List<ObjectSingleAction> everyActions = new ArrayList<>();

    private boolean isEmpty = false;

    public ObjectAction() {
        this.isEmpty = true;
    }

    public ObjectAction(ConfigurationSection section) {
        this.section = section;
        initAction();
    }

    private void initAction() {
        if (section == null) {
            this.isEmpty = true;
            this.section = new MemoryConfiguration();
            return;
        }
        for (String key : section.getKeys(false)) {
            ConfigurationSection singleActionSection = section.getConfigurationSection(key);
            if (singleActionSection == null) {
                continue;
            }
            ObjectSingleAction singleAction = new ObjectSingleAction(this, singleActionSection);
            everyActions.add(singleAction);
        }
        this.isEmpty = everyActions.isEmpty();
    }

    public void runAllActions(Player player, ItemStack original, ItemStack item) {
        for (ObjectSingleAction singleAction : everyActions) {
            singleAction.doAction(player, original, item);
        }
    }

    public void runRandomEveryActions(Player player, ItemStack original, ItemStack item, int x) {
        Collections.shuffle(everyActions);  // 随机打乱动作顺序
        for (int i = 0; i < Math.min(x, everyActions.size()); i++) {
            everyActions.get(i).doAction(player, original, item);  // 执行 x 个随机动作
        }
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}

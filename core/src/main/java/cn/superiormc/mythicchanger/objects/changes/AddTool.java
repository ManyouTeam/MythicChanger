package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Tool;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.set.RegistryKeySet;
import io.papermc.paper.registry.set.RegistrySet;
import net.kyori.adventure.util.TriState;
import org.bukkit.Registry;
import org.bukkit.block.BlockType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class AddTool extends AbstractChangesRule {

    public AddTool() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        if (!CommonUtil.getMajorVersion(21)) {
            return singleChange.getItem();
        }
        ObjectSingleChange toolKey = singleChange.getConfigurationSection("add-tool");
        if (toolKey == null) {
            return singleChange.getItem();
        }

        ItemStack item = singleChange.getItem();
        Tool tool = item.getData(DataComponentTypes.TOOL);
        Tool.Builder builder = Tool.tool();

        float baseMiningSpeed = 1;
        double miningSpeedMultiplier = toolKey.getDouble("mining-speed-multiplier",
                toolKey.getDouble("mining-speed", 1));
        if (tool != null) {
            builder.damagePerBlock(tool.damagePerBlock());
            builder.defaultMiningSpeed(tool.defaultMiningSpeed());
            builder.canDestroyBlocksInCreative(tool.canDestroyBlocksInCreative());
            builder.addRules(tool.rules());
            baseMiningSpeed = tool.defaultMiningSpeed();
            if (toolKey.contains("damage-per-block-add")) {
                builder.damagePerBlock(Math.max(0,
                        tool.damagePerBlock() + toolKey.getInt("damage-per-block-add")));
            }
        }
        if (baseMiningSpeed <= 0) {
            baseMiningSpeed = 1;
        }
        if (miningSpeedMultiplier > 0) {
            builder.defaultMiningSpeed((float) (baseMiningSpeed * miningSpeedMultiplier));
        }
        if (toolKey.contains("damage-per-block")) {
            builder.damagePerBlock(toolKey.getInt("damage-per-block"));
        }
        for (String rule : toolKey.getStringList("rules")) {
            addToolRule(builder, rule, baseMiningSpeed);
        }
        singleChange.setNeedRewriteItem();
        item.setData(DataComponentTypes.TOOL, builder.build());
        return singleChange.getItem();
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("add-tool") == null;
    }

    private void addToolRule(Tool.Builder toolComponent, String rawRule, float baseMiningSpeed) {
        String[] ruleParts = rawRule.replace(" ", "").split(",");
        if (ruleParts.length < 3) {
            return;
        }

        int speedIndex = ruleParts.length - 2;
        float speed = (float) (baseMiningSpeed * Double.parseDouble(ruleParts[speedIndex]));
        Boolean correctForDrops = parseNullableBoolean(ruleParts[ruleParts.length - 1]);

        Set<TypedKey<BlockType>> blockTypeKeys = new HashSet<>();
        for (String singleMaterial : ruleParts) {
            TypedKey<BlockType> key = RegistryKey.BLOCK.typedKey(singleMaterial);
            if (Registry.BLOCK.get(key) == null) {
                break;
            }
            blockTypeKeys.add(key);
        }
        RegistryKeySet<@org.jetbrains.annotations.NotNull BlockType> blockTypes = RegistrySet.keySet(RegistryKey.BLOCK, blockTypeKeys);
        Tool.Rule rule = Tool.rule(blockTypes, speed, TriState.byBoolean(correctForDrops));
        toolComponent.addRule(rule);
    }

    private Boolean parseNullableBoolean(String rawValue) {
        if (rawValue == null || rawValue.equalsIgnoreCase("null")) {
            return null;
        }
        return Boolean.parseBoolean(rawValue);
    }
}

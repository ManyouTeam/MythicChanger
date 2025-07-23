package cn.superiormc.mythicchanger.commands;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.LanguageManager;
import cn.superiormc.mythicchanger.methods.DebuildItem;
import cn.superiormc.mythicchanger.objects.AbstractCommand;
import cn.superiormc.mythicchanger.utils.SchedulerUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class SubGenerateItemFormat extends AbstractCommand {

    public SubGenerateItemFormat() {
        this.id = "generateitemformat";
        this.requiredPermission =  "mythicchanger." + id;
        this.onlyInGame = true;
        this.requiredArgLength = new Integer[]{1};
    }

    @Override
    public void executeCommandInGame(String[] args, Player player) {
        YamlConfiguration itemConfig = new YamlConfiguration();
        DebuildItem.debuildItem(player.getInventory().getItemInMainHand(), itemConfig);
        String yaml = itemConfig.saveToString();
        SchedulerUtil.runTaskAsynchronously(() -> {
            Path path = new File(MythicChanger.instance.getDataFolder(), "generated-item-format.yml").toPath();
            try {
                Files.write(path, yaml.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        LanguageManager.languageManager.sendStringText(player, "plugin.generated");
    }
}

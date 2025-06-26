package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.utils.TextUtil;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class LanguageManager {

    public static LanguageManager languageManager;

    private YamlConfiguration messageFile;

    private YamlConfiguration tempMessageFile;

    private File file;

    private File tempFile;

    public LanguageManager() {
        languageManager = this;
        initLanguage();
    }

    private void initLanguage() {
        this.file = new File(MythicChanger.instance.getDataFolder() + "/languages/" + ConfigManager.configManager.getString("config-files.language", "en_US") + ".yml");
        if (!file.exists()){
            this.file = new File(MythicChanger.instance.getDataFolder(), "message.yml");
            if (!file.exists()) {
                Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §cWe can not found your message file, " +
                        "please try restart your server!");
            }
        }
        else {
            this.messageFile = YamlConfiguration.loadConfiguration(file);
        }
        InputStream is = MythicChanger.instance.getResource("languages/en_US.yml");
        if (is == null) {
            return;
        }
        this.tempFile = new File(MythicChanger.instance.getDataFolder(), "tempMessage.yml");
        try {
            Files.copy(is, tempFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.tempMessageFile = YamlConfiguration.loadConfiguration(tempFile);
        if (messageFile == null) {
            messageFile = tempMessageFile;
        }
        this.tempFile.delete();
    }

    public void sendStringText(CommandSender sender, String... args) {
        if (sender instanceof Player) {
            sendStringText((Player) sender, args);
        }
        else {
            sendStringText(args);
        }
    }

    public void sendStringText(String... args) {
        String text = this.messageFile.getString(args[0]);
        if (text == null) {
            if (this.tempMessageFile.getString(args[0]) == null) {
                Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §cCan not found language key: " + args[0] + "!");
                return;
            }
            else {
                Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §cUpdated your language file, added " +
                        "new language key and it's default value: " + args[0] + "!");
                text = this.tempMessageFile.getString(args[0]);
                messageFile.set(args[0], text);
                try {
                    messageFile.save(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for (int i = 1 ; i < args.length ; i += 2) {
            String var = "{" + args[i] + "}";
            if (args[i + 1] == null) {
                text = text.replace(var, "");
            }
            else {
                text = text.replace(var, args[i + 1]);
            }
        }
        if (text.length() != 0) {
            if (MythicChanger.isPaper && ConfigManager.configManager.getBoolean("paper-api.use-component.message")) {
                Bukkit.getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize(text));
            } else {
                Bukkit.getConsoleSender().sendMessage(TextUtil.parse(text));
            }
        }
    }

    public void sendStringText(Player player, String... args) {
        String text = this.messageFile.getString(args[0]);
        if (text == null) {
            if (this.tempMessageFile.getString(args[0]) == null) {
                player.sendMessage(TextUtil.pluginPrefix() + " §cCan not found language key: " + args[0] + "!");
                return;
            }
            else {
                Bukkit.getConsoleSender().sendMessage(TextUtil.pluginPrefix() + " §cUpdated your language file, added " +
                        "new language key and it's default value: " + args[0] + "!");
                text = this.tempMessageFile.getString(args[0]);
                messageFile.set(args[0], text);
                try {
                    messageFile.save(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for (int i = 1 ; i < args.length ; i += 2) {
            String var = "{" + args[i] + "}";
            if (args[i + 1] == null) {
                text = text.replace(var, "");
            }
            else {
                text = text.replace(var, args[i + 1]);
            }
        }
        if (text.length() != 0) {
            if (MythicChanger.isPaper && ConfigManager.configManager.getBoolean("paper-api.use-component.message")) {
                player.sendMessage(MiniMessage.miniMessage().deserialize(text));
            } else {
                player.sendMessage(TextUtil.parse(text, player));
            }
        }

    }

}

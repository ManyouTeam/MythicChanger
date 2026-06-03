package cn.superiormc.mythicchanger.objects;

import cn.superiormc.mythicchanger.manager.LanguageManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand {

    protected String id;

    protected String requiredPermission;

    protected boolean onlyInGame;

    protected Integer[] requiredArgLength;

    protected Integer[] requiredConsoleArgLength;

    protected boolean unlimitedArgLength;

    public AbstractCommand() {
        // EMPTY
    }

    public abstract void executeCommandInGame(String[] args, Player player);

    public void executeCommandInConsole(String[] args) {
        LanguageManager.languageManager.sendStringText("error.in-game");
    }

    public String getId() {
        return id;
    }

    public boolean getOnlyInGame() {
        return onlyInGame;
    }

    public String getRequiredPermission() {
        return requiredPermission;
    }

    public boolean getLengthCorrect(int length, CommandSender sender) {
        if (requiredConsoleArgLength == null || requiredConsoleArgLength.length == 0) {
            requiredConsoleArgLength = requiredArgLength;
        }
        if (sender instanceof Player) {
            for (int number : requiredArgLength) {
                if (unlimitedArgLength) {
                    return length >= number;
                }
                if (number == length) {
                    return true;
                }
            }
        } else {
            for (int number : requiredConsoleArgLength) {
                if (unlimitedArgLength) {
                    return length >= number;
                }
                if (number == length) {
                    return true;
                }
            }
        }
        return false;
    }


    public List<String> getTabResult(int length, Player player) {
        return new ArrayList<>();
    }
}

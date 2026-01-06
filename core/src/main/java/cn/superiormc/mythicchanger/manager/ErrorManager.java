package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class ErrorManager {

    public static ErrorManager errorManager;

    public boolean getError = false;

    private String lastErrorMessage = "";

    public ErrorManager(){
        errorManager = this;
    }

    public void sendErrorMessage(String message){
        if (!getError || !message.equals(lastErrorMessage)) {
            TextUtil.sendMessage(null, TextUtil.pluginPrefix() + " " + message);
            lastErrorMessage = message;
            getError = true;
            try {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        getError = false;
                    }
                }.runTaskLater(MythicChanger.instance, 100);
            } catch (Exception ignored) {
            }
        }
    }
}

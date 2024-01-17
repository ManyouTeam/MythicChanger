package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
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
            Bukkit.getConsoleSender().sendMessage(message);
            lastErrorMessage = message;
            getError = true;
            new BukkitRunnable() {
                @Override
                public void run() {
                    getError = false;
                }
            }.runTaskLater(MythicChanger.instance, 100);
        }
    }
}

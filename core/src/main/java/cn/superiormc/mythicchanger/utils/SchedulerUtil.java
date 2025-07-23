package cn.superiormc.mythicchanger.utils;

import cn.superiormc.mythicchanger.MythicChanger;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class SchedulerUtil {

    private BukkitTask bukkitTask;

    private ScheduledTask scheduledTask;

    public SchedulerUtil(BukkitTask bukkitTask) {
        this.bukkitTask = bukkitTask;
    }

    public SchedulerUtil(ScheduledTask scheduledTask) {
        this.scheduledTask = scheduledTask;
    }

    public void cancel() {
        if (MythicChanger.isFolia) {
            scheduledTask.cancel();
        } else {
            bukkitTask.cancel();
        }
    }

    // 在主线程上运行任务
    public static void runSync(Runnable task) {
        if (MythicChanger.isFolia) {
            Bukkit.getGlobalRegionScheduler().execute(MythicChanger.instance, task);
        } else {
            Bukkit.getScheduler().runTask(MythicChanger.instance, task);
        }
    }

    // 在异步线程上运行任务
    public static void runTaskAsynchronously(Runnable task) {
        if (MythicChanger.isFolia) {
            task.run();
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(MythicChanger.instance, task);
        }
    }

    // 延迟执行任务
    public static SchedulerUtil runTaskLater(Runnable task, long delayTicks) {
        if (MythicChanger.isFolia) {
            return new SchedulerUtil(Bukkit.getGlobalRegionScheduler().runDelayed(MythicChanger.instance,
                    scheduledTask -> task.run(), delayTicks));
        } else {
            return new SchedulerUtil(Bukkit.getScheduler().runTaskLater(MythicChanger.instance, task, delayTicks));
        }
    }

    // 定时循环任务
    public static SchedulerUtil runTaskTimer(Runnable task, long delayTicks, long periodTicks) {
        if (MythicChanger.isFolia) {
            return new SchedulerUtil(Bukkit.getGlobalRegionScheduler().runAtFixedRate(MythicChanger.instance,
                    scheduledTask -> task.run(), delayTicks, periodTicks));
        } else {
            return new SchedulerUtil(Bukkit.getScheduler().runTaskTimer(MythicChanger.instance, task, delayTicks, periodTicks));
        }
    }

    // 延迟执行任务
    public static SchedulerUtil runTaskLaterAsynchronously(Runnable task, long delayTicks) {
        if (MythicChanger.isFolia) {
            return new SchedulerUtil(Bukkit.getGlobalRegionScheduler().runDelayed(MythicChanger.instance,
                    scheduledTask -> task.run(), delayTicks));
        } else {
            return new SchedulerUtil(Bukkit.getScheduler().runTaskLaterAsynchronously(MythicChanger.instance, task, delayTicks));
        }
    }

}

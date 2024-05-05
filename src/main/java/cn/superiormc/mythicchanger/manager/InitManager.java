package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.utils.CommonUtil;

import java.io.File;

public class InitManager {
    public static InitManager initManager;

    private boolean firstLoad = false;

    public InitManager() {
        initManager = this;
        File file = new File(MythicChanger.instance.getDataFolder() + "/rules/", "default.yml");
        if (!file.exists()) {
            firstLoad = true;
        }
        init();
    }

    public void init() {
        resourceOutput("languages/en_US.yml", true);
        resourceOutput("rules/default.yml", false);
    }
    private void resourceOutput(String fileName, boolean fix) {
        File tempVal1 = new File(MythicChanger.instance.getDataFolder(), fileName);
        if (!tempVal1.exists()) {
            if (!firstLoad && !fix) {
                return;
            }
            File tempVal2 = new File(fileName);
            if (tempVal2.getParentFile() != null) {
                CommonUtil.mkDir(tempVal2.getParentFile());
            }
            MythicChanger.instance.saveResource(tempVal2.getPath(), false);
        }
    }
}

package cn.superiormc.mythicchanger.manager;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.utils.CommonUtil;

import java.io.File;

public class InitManager {
    public static InitManager initManager;

    private boolean firstLoad = false;

    public InitManager() {
        initManager = this;
        File file = new File(MythicChanger.instance.getDataFolder(), "config.yml");
        if (!file.exists()) {
            MythicChanger.instance.saveDefaultConfig();
            firstLoad = true;
        }
        init();
    }

    public void init() {
        resourceOutput("languages/en_US.yml", true);
        resourceOutput("languages/zh_CN.yml", true);
        resourceOutput("rules/example.yml", false);
        resourceOutput("apply_items/ItemSkin.yml", false);
        resourceOutput("apply_items/Deapply.yml", false);
        resourceOutput("apply_items/ComplexUsageExample.yml", false);
        resourceOutput("apply_items/attributescroll/attributescroll_attackdamage.yml", false);
        resourceOutput("apply_items/enchantscroll/enchantscroll_fortune.yml", false);
        resourceOutput("apply_items/enchantscroll/enchantscroll_looting.yml", false);
        resourceOutput("apply_items/enchantscroll/enchantscroll_power.yml", false);
        resourceOutput("apply_items/enchantscroll/enchantscroll_protection.yml", false);
        resourceOutput("apply_items/enchantscroll/enchantscroll_respiration.yml", false);
        resourceOutput("apply_items/enchantscroll/enchantscroll_sharpness.yml", false);
        resourceOutput("apply_items/enchantscroll/enchantscroll_unbreaking.yml", false);
    }

    public boolean isFirstLoad() {
        return firstLoad;
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

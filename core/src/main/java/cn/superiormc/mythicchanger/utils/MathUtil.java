package cn.superiormc.mythicchanger.utils;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.manager.ConfigManager;
import cn.superiormc.mythicchanger.manager.ErrorManager;
import redempt.crunch.Crunch;
import redempt.crunch.functional.ExpressionEnv;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MathUtil {

    public static int scale = ConfigManager.configManager.getInt("math.scale", 2);

    public static BigDecimal doCalculate(String mathStr) {
        return doCalculate(mathStr, scale);
    }

    public static BigDecimal doCalculate(String mathStr, int scale) {
        try {
            if (MythicChanger.freeVersion || !ConfigManager.configManager.getBoolean("math.enabled")) {
                return new BigDecimal(mathStr);
            }
            if (ConfigManager.configManager.getBoolean("math.enable-function")) {
                ExpressionEnv env = new ExpressionEnv();
                env.addFunction("sum", d -> {
                    double s = 0;
                    for (double v : d) {
                        s += v;
                    }
                    return s;
                });
                env.addFunction("max", d -> {
                    if (d.length == 0) {
                        return 0;
                    }
                    double m = d[0];
                    for (int i = 1; i < d.length; i++) {
                        m = Math.max(m, d[i]);
                    }
                    return m;
                });
                env.addFunction("min", d -> {
                    if (d.length == 0) return 0;
                    double m = d[0];
                    for (int i = 1; i < d.length; i++) {
                        m = Math.min(m, d[i]);
                    }
                    return m;
                });
                env.addFunction("avg", d -> {
                    if (d.length == 0) {
                        return 0;
                    }
                    double s = 0;
                    for (double v : d) {
                        s += v;
                    }
                    return s / d.length;
                });
                env.addFunction("random", d -> {
                    ThreadLocalRandom random = ThreadLocalRandom.current();

                    // random()：0 到 1
                    if (d.length == 0) {
                        return random.nextDouble();
                    }

                    // random(max)：0 到 max
                    if (d.length == 1) {
                        double min = Math.min(0, d[0]);
                        double max = Math.max(0, d[0]);
                        return min == max ? min : random.nextDouble(min, max);
                    }

                    // random(min, max)
                    double min = Math.min(d[0], d[1]);
                    double max = Math.max(d[0], d[1]);
                    return min == max ? min : random.nextDouble(min, max);
                });
                return BigDecimal.valueOf(Crunch.compileExpression(mathStr, env).evaluate()).setScale(scale, RoundingMode.HALF_UP);
            }
            return BigDecimal.valueOf(Crunch.evaluateExpression(mathStr)).setScale(scale, RoundingMode.HALF_UP);
        }
        catch (Throwable throwable) {
            if (ConfigManager.configManager.getBoolean("debug")) {
                throwable.printStackTrace();
            }
            ErrorManager.errorManager.sendErrorMessage("§cError: Your number option value " +
                    mathStr + " can not be read as a number, maybe " +
                    "set math.enabled to false in config.yml maybe solve this problem!");
            return BigDecimal.ZERO;
        }
    }

    public static List<BigDecimal> transferMathList(List<String> stringList) {
        List<BigDecimal> tempVal1 = new ArrayList<>();
        for (String tempVa2 : stringList) {
            try {
                tempVal1.add(new BigDecimal(tempVa2));
            } catch (NumberFormatException ep) {
                ErrorManager.errorManager.sendErrorMessage("§cError: Your number option value " +
                        tempVa2 + " can not be read as a number, maybe" +
                        "set math.enabled to false in config.yml maybe solve this problem!");
                tempVal1.add(new BigDecimal(-1));
            }
        }
        return tempVal1;
    }
}

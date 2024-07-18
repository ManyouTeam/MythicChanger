package cn.superiormc.mythicchanger.objects;

import cn.superiormc.mythicchanger.manager.ErrorManager;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.ItemUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ObjectAction {

    private final List<String> everyAction = new ArrayList<>();

    private boolean isEmpty = false;

    public ObjectAction() {
        this.isEmpty = true;
    }

    public ObjectAction(List<String> action) {
        this.isEmpty = action.isEmpty();
        everyAction.addAll(action);
    }

    public void doAction(Player player, ItemStack original, ItemStack item) {
        if (everyAction.isEmpty()) {
            return;
        }
        checkAction(player, everyAction, original, item);
    }

    private void checkAction(Player player, List<String> actions, ItemStack original, ItemStack item) {
        if (player == null) {
            return;
        }
        for (String singleAction : actions) {
            singleAction = replacePlaceholder(singleAction, player, original, item);
            String[] splits = singleAction.split(";;");
            if (singleAction.startsWith("none")) {
                return;
            } else if (singleAction.startsWith("sound: ")) {
                // By: iKiwo
                String soundData = singleAction.substring(7); // "sound: LEVEL_UP;volume;pitch"
                String[] soundParts = soundData.split(";;");
                if (soundParts.length >= 1) {
                    String soundName = soundParts[0];
                    float volume = 1.0f;
                    float pitch = 1.0f;
                    if (soundParts.length >= 2) {
                        try {
                            volume = Float.parseFloat(soundParts[1]);
                        } catch (NumberFormatException e) {
                            ErrorManager.errorManager.sendErrorMessage("§x§9§8§F§B§9§8[MythicChanger] §cError: Invalid volume value in sound action.");
                        }
                    }
                    if (soundParts.length >= 3) {
                        try {
                            pitch = Float.parseFloat(soundParts[2]);
                        } catch (NumberFormatException e) {
                            ErrorManager.errorManager.sendErrorMessage("§x§9§8§F§B§9§8[MythicChanger] §cError: Invalid pitch value in sound action.");
                        }
                    }
                    Location location = player.getLocation();
                    player.playSound(location, soundName, volume, pitch);
                } else {
                    ErrorManager.errorManager.sendErrorMessage("§x§9§8§F§B§9§8[MythicChanger] §cError: Invalid sound action format.");
                }
            } else if (singleAction.startsWith("message: ")) {
                player.sendMessage(TextUtil.parse(singleAction.substring(9)));
            } else if (singleAction.startsWith("announcement: ")) {
                Collection<? extends Player> players = Bukkit.getOnlinePlayers();
                for (Player p : players) {
                    p.sendMessage(TextUtil.parse(singleAction.substring(14)));
                }
            } else if (singleAction.startsWith("effect: ") && splits.length == 3) {
                PotionEffectType potionEffectType = PotionEffectType.getByName(singleAction.substring(8).split(";;")[0].toUpperCase());
                if (potionEffectType == null) {
                    ErrorManager.errorManager.sendErrorMessage("§x§9§8§F§B§9§8[MythicChanger] §cError: Can not found potion effect: " +
                            singleAction.substring(8).split(";;")[0] + ".");
                    continue;
                }
                PotionEffect effect = new PotionEffect(potionEffectType,
                        Integer.parseInt(singleAction.substring(8).split(";;")[2]),
                        Integer.parseInt(singleAction.substring(8).split(";;")[1]) - 1,
                        true,
                        true,
                        true);
                player.addPotionEffect(effect);
            } else if (singleAction.startsWith("entity_spawn: ")) {
                if (singleAction.split(";;").length == 1) {
                    EntityType entity = EntityType.valueOf(singleAction.substring(14).split(";;")[0].toUpperCase());
                    Location location = player.getLocation();
                    location.getWorld().spawnEntity(player.getLocation(), entity);
                } else if (singleAction.split(";;").length == 5) {
                    World world = Bukkit.getWorld(singleAction.substring(14).split(";;")[1]);
                    Location location = new Location(world,
                            Double.parseDouble(singleAction.substring(14).split(";;")[2]),
                            Double.parseDouble(singleAction.substring(14).split(";;")[3]),
                            Double.parseDouble(singleAction.substring(14).split(";;")[4]));
                    EntityType entity = EntityType.valueOf(singleAction.substring(14).split(";;")[0].toUpperCase());
                    if (location.getWorld() == null) {
                        ErrorManager.errorManager.sendErrorMessage("§x§9§8§F§B§9§8[MythicChanger] §cError: Your entity_spawn action in shop configs can not being correctly load.");
                        continue;
                    }
                    location.getWorld().spawnEntity(location, entity);
                }
            } else if (singleAction.startsWith("teleport: ")) {
                if (singleAction.split(";;").length == 4) {
                    Location loc = new Location(Bukkit.getWorld(singleAction.substring(10).split(";;")[0]),
                            Double.parseDouble(singleAction.substring(10).split(";;")[1]),
                            Double.parseDouble(singleAction.substring(10).split(";;")[2]),
                            Double.parseDouble(singleAction.substring(10).split(";;")[3]),
                            player.getLocation().getYaw(),
                            player.getLocation().getPitch());
                    player.teleport(loc);
                }
                else if (singleAction.split(";;").length == 6) {
                    Location loc = new Location(Bukkit.getWorld(singleAction.split(";;")[0]),
                            Double.parseDouble(singleAction.substring(10).split(";;")[1]),
                            Double.parseDouble(singleAction.substring(10).split(";;")[2]),
                            Double.parseDouble(singleAction.substring(10).split(";;")[3]),
                            Float.parseFloat(singleAction.substring(10).split(";;")[4]),
                            Float.parseFloat(singleAction.substring(10).split(";;")[5]));
                    player.teleport(loc);
                }
                else {
                    ErrorManager.errorManager.sendErrorMessage("§x§9§8§F§B§9§8[MythicChanger] §cError: Your teleport action in shop configs can not being correctly load.");
                }
            } else if (CommonUtil.checkPluginLoad("MythicMobs") && singleAction.startsWith("mythicmobs_spawn: ")) {
                if (singleAction.substring(18).split(";;").length == 1) {
                    CommonUtil.summonMythicMobs(player.getLocation(),
                            singleAction.substring(18).split(";;")[0],
                            1);
                }
                else if (singleAction.substring(18).split(";;").length == 2) {
                    CommonUtil.summonMythicMobs(player.getLocation(),
                            singleAction.substring(18).split(";;")[0],
                            Integer.parseInt(singleAction.substring(18).split(";;")[1]));
                }
                else if (singleAction.substring(18).split(";;").length == 5) {
                    World world = Bukkit.getWorld(singleAction.substring(18).split(";;")[1]);
                    Location location = new Location(world,
                            Double.parseDouble(singleAction.substring(18).split(";;")[2]),
                            Double.parseDouble(singleAction.substring(18).split(";;")[3]),
                            Double.parseDouble(singleAction.substring(18).split(";;")[4])
                    );
                    CommonUtil.summonMythicMobs(location,
                            singleAction.substring(18).split(";;")[0],
                            1);
                }
                else if (singleAction.substring(18).split(";;").length == 6) {
                    World world = Bukkit.getWorld(singleAction.substring(18).split(";;")[2]);
                    Location location = new Location(world,
                            Double.parseDouble(singleAction.substring(18).split(";;")[3]),
                            Double.parseDouble(singleAction.substring(18).split(";;")[4]),
                            Double.parseDouble(singleAction.substring(18).split(";;")[5])
                    );
                    CommonUtil.summonMythicMobs(location,
                            singleAction.substring(18).split(";;")[0],
                            Integer.parseInt(singleAction.substring(18).split(";;")[1]));
                }
                else {
                    ErrorManager.errorManager.sendErrorMessage("§x§9§8§F§B§9§8[UltimateShop] §cError: Your mythicmobs_spawn action in shop configs can not being correctly load.");
                }
            } else if (singleAction.startsWith("console_command: ")) {
                CommonUtil.dispatchCommand(singleAction.substring(17));
            } else if (singleAction.startsWith("player_command: ")) {
                CommonUtil.dispatchCommand(player, singleAction.substring(16));
            } else if (singleAction.startsWith("op_command: ")) {
                CommonUtil.dispatchOpCommand(player, singleAction.substring(12));
            }
        }
    }

    private String replacePlaceholder(String str, Player player, ItemStack original, ItemStack item) {
        str = str.replace("{world}", player.getWorld().getName())
                .replace("{player_x}", String.valueOf(player.getLocation().getX()))
                .replace("{player_y}", String.valueOf(player.getLocation().getY()))
                .replace("{player_z}", String.valueOf(player.getLocation().getZ()))
                .replace("{player_pitch}", String.valueOf(player.getLocation().getPitch()))
                .replace("{player_yaw}", String.valueOf(player.getLocation().getYaw()))
                .replace("{player}", player.getName()
                .replace("{original-name}", ItemUtil.getItemName(original)
                .replace("{name}", ItemUtil.getItemName(item))));
        if (CommonUtil.checkPluginLoad("PlaceholderAPI")) {
            str = PlaceholderAPI.setPlaceholders(player, str);
        }
        return str;
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}

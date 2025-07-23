package cn.superiormc.mythicchanger.objects.actions;

import cn.superiormc.mythicchanger.utils.CommonUtil;
import org.bukkit.Registry;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ActionEffect extends AbstractRunAction {

    public ActionEffect() {
        super("effect");
        setRequiredArgs("potion", "duration", "level");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, ItemStack original, ItemStack item) {
        PotionEffectType potionEffectType;
        if (CommonUtil.getMajorVersion(20)) {
            potionEffectType = Registry.POTION_EFFECT_TYPE.get(CommonUtil.parseNamespacedKey(singleAction.getString("potion")));
        } else {
            potionEffectType = PotionEffectType.getByName(singleAction.getString("potion"));
        }
        if (potionEffectType != null) {
            PotionEffect effect = new PotionEffect(potionEffectType,
                    singleAction.getInt("duration"),
                    singleAction.getInt("level"),
                    singleAction.getBoolean("ambient", true),
                    singleAction.getBoolean("particles", true),
                    singleAction.getBoolean("icon", true));
            player.addPotionEffect(effect);
        }
    }
}

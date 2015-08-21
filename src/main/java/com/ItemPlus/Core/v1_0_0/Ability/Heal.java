package com.ItemPlus.Core.v1_0_0.Ability;

import com.ItemPlus.Core.v1_0_0.Utils.TargetHelper;
import com.ItemPlus.Event.Item.Ability.AbilitySpellEvent;
import com.ItemPlus.Item.Ability.Ability;
import com.ItemPlus.Item.Ability.AbilityInfo;
import com.ItemPlus.ItemPlus;
import org.bukkit.entity.LivingEntity;

import static org.bukkit.Bukkit.getServer;

/**
 * <p>治疗一个玩家。</p>
 * <p>Healing a player</p>
 *
 * @author SettingDust
 */
public final class Heal extends Ability {
    private int value;
    private int range;

    /**
     * <p>构造技能<p/>
     *
     * @param info  技能信息
     * @param value 治疗量
     * @param range 范围
     */
    public Heal(AbilityInfo info, int value, int range) {
        super(info);
        this.value = value;
        this.range = range;
    }

    @Override
    public void onSpell() {
        if (this.getAbilityInfo().getLocation() != null) {
            AbilitySpellEvent event = new AbilitySpellEvent(this);
            getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return;
            }

            ItemPlus.getAbilityManager().getAbilityMap().put(this.getUniqueId(), this);
            LivingEntity target = TargetHelper.getLivingTarget(this.getAbilityInfo().getPlayer(), range);
            target.setHealth(target.getHealth() + this.value);
        }
    }

    /**
     * @return int
     */
    public int getValue() {
        return value;
    }

    /**
     * @return int
     */
    public int getRange() {
        return range;
    }
}

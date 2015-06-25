/*
 * Copyright (C) 2015 HotFlow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ItemPlus.Core.v1_0_0.Ability;

import com.ItemPlus.Item.Ability.Ability;
import com.ItemPlus.Item.Ability.AbilityInfo;
import com.ItemPlus.Item.Ability.AbilityType;
import com.ItemPlus.ItemPlus;
import java.util.Collections;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Fireball;
import org.bukkit.event.block.Action;

/**
 * 火球术，向前方发射一个火球
 * <p>
 * @author HotFlow
 */
public final class FireBall implements Ability
{
    private Fireball ball;
    private final double damage;
    private final List<Action> actions;
    private final long cooldown;
    private final int durabilityCast;

    /**
     * 构造火球术
     * <p>
     * @param damage 伤害
     * @param actions 执行方法
     * @param cooldown 冷却
     * @param durabilityCast 耐久消耗
     */
    public FireBall(double damage, List<Action> actions, long cooldown, int durabilityCast)
    {
        this.ball = null;
        this.damage = damage;
        this.actions = actions;
        this.cooldown = cooldown;
        this.durabilityCast = durabilityCast;
        ItemPlus.getAbilityManager().getAbilityList().add(this);
    }

    @Override
    public AbilityType getAbilityType()
    {
        return AbilityType.Point;
    }

    @Override
    public List<Action> getActions()
    {
        return Collections.unmodifiableList(this.actions);
    }

    @Override
    public long getCooldown()
    {
        return this.cooldown;
    }

    @Override
    public int getDurabilityCast()
    {
        return this.durabilityCast;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onAbility(AbilityInfo info)
    {
        Location from = info.getPlayer().getLocation();
        from.setY(from.getY() + 1);
        this.ball = info.getPlayer().getWorld().spawn(from, Fireball.class);
        this.ball.setShooter(info.getPlayer());
        this.ball.setDirection(from.subtract(info.getLocation()).getDirection());
    }

    /**
     * 获取伤害
     * <p>
     * @return double
     */
    public double getDamage()
    {
        return this.damage;
    }

    /**
     * 获取火球
     * <p>
     * @return Fireball
     */
    public Fireball getFireball()
    {
        return this.ball;
    }

}

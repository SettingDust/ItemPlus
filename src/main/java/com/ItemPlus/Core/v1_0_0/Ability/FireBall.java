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
import org.bukkit.Location;
import org.bukkit.entity.Fireball;

/**
 * 火球术，向前方发射一个火球
 * <p>
 * @author HotFlow
 */
public final class FireBall extends Ability
{
    private Fireball ball;
    private final double damage;
    private final double explodeDamage;

    /**
     * 构造火球术
     * <p>
     * @param damage 伤害
     * @param explodeDamage 爆炸伤害
     * @param cooldown 冷却
     * @param durabilityCast 耐久消耗
     */
    public FireBall(double damage, double explodeDamage, long cooldown, int durabilityCast)
    {
        super(cooldown, durabilityCast);
        this.ball = null;
        this.damage = damage;
        this.explodeDamage = explodeDamage;
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
     * 获取爆炸伤害
     * <p>
     * @return double
     */
    public double getExplodeDamage()
    {
        return this.explodeDamage;
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

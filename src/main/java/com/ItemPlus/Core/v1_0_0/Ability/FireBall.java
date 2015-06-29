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

import com.ItemPlus.Event.Item.Ability.AbilityEndedEvent;
import com.ItemPlus.Event.Item.Ability.AbilitySpellEvent;
import com.ItemPlus.Item.Ability.Ability;
import com.ItemPlus.Item.Ability.AbilityInfo;
import com.ItemPlus.ItemPlus;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.Location;
import org.bukkit.entity.Fireball;

/**
 * 火球术
 * <p>
 * 向前方释放一颗火球，对目标位置造成爆炸伤害。
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
     * @param info 技能信息
     */
    public FireBall(double damage, double explodeDamage, AbilityInfo info)
    {
        super(info);
        this.ball = null;
        this.damage = damage;
        this.explodeDamage = explodeDamage;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onSpell()
    {
        if (this.getAbilityInfo().getLocation() != null)
        {
            AbilitySpellEvent event = new AbilitySpellEvent(this);
            getServer().getPluginManager().callEvent(event);

            if (event.isCancelled())
            {
                return;
            }

            ItemPlus.getAbilityManager().getAbilityMap().put(this.getUniqueId(), this);

            final Location from = this.getAbilityInfo().getPlayer().getLocation();
            from.setY(from.getY() + 1);
            this.ball = this.getAbilityInfo().getPlayer().getWorld().spawn(from, Fireball.class);
            this.ball.setShooter(this.getAbilityInfo().getPlayer());
            this.ball.setDirection(from.subtract(this.getAbilityInfo().getLocation()).getDirection());
        }
    }

    @Override
    public void onEnded()
    {
        ItemPlus.getAbilityManager().getAbilityMap().remove(this.getUniqueId());

        AbilityEndedEvent event = new AbilityEndedEvent(this);
        getServer().getPluginManager().callEvent(event);
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

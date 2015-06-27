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

package com.ItemPlus.Event.Item.Ability;

import com.ItemPlus.Item.Ability.Ability;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;

/**
 * 技能伤害生物事件
 * <p>
 * @author HotFlow
 */
public class AbilityDamageEntityEvent extends AbilityEvent implements Cancellable
{
    private final Entity entity;
    private double damage;
    private Boolean cancelled = false;

    /**
     * 构造技能伤害事件
     * <p>
     * @param ability 技能
     * @param entity 生物
     * @param damage 伤害
     */
    public AbilityDamageEntityEvent(Ability ability, Entity entity, double damage)
    {
        super(ability);
        this.entity = entity;
        this.damage = damage;
    }

    /**
     * 获取生物
     * <p>
     * @return Entity
     */
    public Entity getEntity()
    {
        return this.entity;
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
     * 设置伤害
     * <p>
     * @param damage 伤害
     */
    public void setDamage(double damage)
    {
        this.damage = damage;
    }
    
    @Override
    public boolean isCancelled()
    {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean bln)
    {
        this.cancelled = bln;
    }
}

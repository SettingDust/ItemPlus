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

package com.ItemPlus.Item.Ability;

import java.util.UUID;

/**
 * 抽象技能
 * <p>
 * @author HotFlow
 */
public abstract class Ability
{
    private final AbilityInfo info;
    private final long cooldown;
    private final int durabilityCast;
    private final UUID uuid;

    /**
     * 构造技能
     * <p>
     * @param info 技能信息
     * @param cooldown 冷却
     * @param durabilityCast 耐久消耗
     */
    public Ability(AbilityInfo info, long cooldown, int durabilityCast)
    {
        this.info = info;
        this.cooldown = cooldown;
        this.durabilityCast = durabilityCast;
        this.uuid = UUID.randomUUID();
    }

    /**
     * 获取技能信息
     * <p>
     * @return AbilityInfo
     */
    public AbilityInfo getAbilityInfo()
    {
        return this.info;
    }

    /**
     * 获取冷却时间
     * <p>
     * @return long
     */
    public long getCooldown()
    {
        return this.cooldown;
    }

    /**
     * 获取耐久消耗
     * <p>
     * @return int
     */
    public int getDurabilityCast()
    {
        return this.durabilityCast;
    }

    /**
     * 获取UUID
     * <p>
     * @return UUID
     */
    public UUID getUniqueId()
    {
        return this.uuid;
    }

    /**
     * 当技能施法时
     */
    public abstract void onSpell();

    /**
     * 当技能结束时
     */
    public abstract void onEnded();
}

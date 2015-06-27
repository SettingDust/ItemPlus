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

import com.ItemPlus.Item.Ability.Buff.Buff;
import java.util.List;
import java.util.UUID;

/**
 * 抽象技能
 * <p>
 * @author HotFlow
 */
public abstract class Ability
{
    private final AbilityInfo info;
    private long time;
    private final UUID uuid;

    /**
     * 构造技能
     * <p>
     * @param info 技能信息
     */
    public Ability(AbilityInfo info)
    {
        this.info = info;
        this.time = info.getCooldown();
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
     * 获取剩余冷却时间
     * <p>
     * @return long
     */
    public long getTime()
    {
        return this.time;
    }

    /**
     * 设置剩余冷却时间
     * <p>
     * @param time 时间
     */
    public void setTime(long time)
    {
        this.time = time;
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

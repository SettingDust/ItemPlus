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

package com.ItemPlus.Item.Ability.Buff;

import java.util.UUID;

/**
 * 状态
 * <p>
 * @author HotFlow
 */
public class Buff
{
    private final BuffInfo info;
    private final UUID uuid;
    private long time;

    public Buff(BuffInfo info)
    {
        this.info = info;
        this.uuid = UUID.randomUUID();
        this.time = info.getTime();
    }

    /**
     * 获取状态信息
     * <p>
     * @return BuffInfo
     */
    public BuffInfo getBuffInfo()
    {
        return this.info;
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
     * 获取时间
     * <p>
     * @return long
     */
    public long getTime()
    {
        return this.time;
    }

    /**
     * 设置时间
     * <p>
     * @param time 时间
     */
    public void setTime(long time)
    {
        this.time = time;
    }
}

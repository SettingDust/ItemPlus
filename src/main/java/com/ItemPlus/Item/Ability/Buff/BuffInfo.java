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

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * 状态信息
 * <p>
 * @author HotFlow
 */
public class BuffInfo
{
    private final Player player;
    private final Entity entity;
    private final long time;

    /**
     * 构造状态信息
     * <p>
     * @param player 玩家
     * @param entity 生物
     * @param time 时间
     */
    public BuffInfo(Player player, Entity entity, long time)
    {
        this.player = player;
        this.entity = entity;
        this.time = time;
    }

    /**
     * 获取玩家
     * <p>
     * @return Player
     */
    public Player getPlayer()
    {
        return this.player;
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
     * 获取时间
     * <p>
     * @return long
     */
    public long getTime()
    {
        return this.time;
    }
}

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

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * 技能信息
 * <p>
 * @author HotFlow
 */
public final class AbilityInfo
{
    private final AbilityType type;
    private final Player player;
    private final Location loc;
    private final Entity entity;

    protected AbilityInfo(AbilityType type, Player player, Location loc, Entity entity)
    {
        this.type = type;
        this.player = player;
        this.loc = loc;
        this.entity = entity;
    }

    /**
     * 构造技能信息
     * <p>
     * @param type 技能类型
     * @param player 玩家
     * @throws java.lang.Exception
     */
    public AbilityInfo(AbilityType type, Player player) throws Exception
    {
        this(type, player, null, null);

        if (!type.equals(AbilityType.Self))
        {
            throw new Exception("技能类型不能为 " + type.name());
        }
    }

    /**
     * 构造技能信息
     * <p>
     * @param type 技能类型
     * @param player 玩家
     * @param loc 坐标
     * @throws java.lang.Exception
     */
    public AbilityInfo(AbilityType type, Player player, Location loc) throws Exception
    {
        this(type, player, loc, null);

        if (!type.equals(AbilityType.Point))
        {
            throw new Exception("技能类型不能为 " + type.name());
        }
    }

    /**
     * 构造技能信息
     * <p>
     * @param type 技能类型
     * @param player 玩家
     * @param entity 目标
     * @throws java.lang.Exception
     */
    public AbilityInfo(AbilityType type, Player player, Entity entity) throws Exception
    {
        this(type, player, null, entity);

        if (!type.equals(AbilityType.Entity))
        {
            throw new Exception("技能类型不能为 " + type.name());
        }
    }

    /**
     * 获取技能类型
     * <p>
     * @return AbilityType
     */
    public AbilityType getAbilityType()
    {
        return this.type;
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
     * 获取坐标点
     * <p>
     * @return Location
     * @throws NullPointerException
     */
    public Location getLocation() throws NullPointerException
    {
        return this.loc;
    }

    /**
     * 获取目标
     * <p>
     * @return Entity
     * @throws NullPointerException
     */
    public Entity getEntity() throws NullPointerException
    {
        return this.entity;
    }
}

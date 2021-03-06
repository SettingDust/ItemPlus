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

package com.ItemPlus.Event.Entity;

import com.ItemPlus.Core.v1_0_0.Entity.ItemEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 生物事件
 * <p>
 * @author HotFlow
 */
public class EntityEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private final ItemEntity entity;

    public EntityEvent(ItemEntity entity)
    {
        this.entity = entity;
    }

    /**
     * 获取生物
     * <p>
     * @return ItemEntity
     */
    public ItemEntity getEntity()
    {
        return this.entity;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    /**
     * 获取处理者列表
     * <p>
     * @return HandlerList
     */
    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}

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

package com.ItemPlus.Event.Player;

import com.ItemPlus.Core.v0_0_1.Entity.ItemPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 玩家事件
 * <p>
 * @author HotFlow
 */
public class PlayerEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private final ItemPlayer player;

    /**
     * 构造玩家事件
     * <p>
     * @param player 玩家
     */
    public PlayerEvent(ItemPlayer player)
    {
        this.player = player;
    }

    /**
     * 获取玩家
     * <p>
     * @return Player
     */
    public ItemPlayer getPlayer()
    {
        return this.player;
    }

    /**
     * 获取处理者
     * <p>
     * @return HandlerList
     */
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

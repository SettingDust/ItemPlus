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

package com.ItemPlus.Core.v1_0_0.Manager;

import com.ItemPlus.Core.v1_0_0.Entity.ItemPlayer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 玩家管理器
 * <p>
 * @author HotFlow
 */
public final class PlayerManager
{
    private final List<ItemPlayer> playerList = new ArrayList<ItemPlayer>();

    /**
     * 获取玩家
     * <p>
     * @param uuid UniqueID
     * @return ItemPlayer
     */
    public ItemPlayer getPlayer(UUID uuid)
    {
        for (ItemPlayer player : this.playerList)
        {
            if (player.getCraftPlayer() != null && player.getCraftPlayer().isOnline())
            {
                if (player.getCraftPlayer().getUniqueId().equals(uuid))
                {
                    return player;
                }
            }
        }

        return null;
    }

    /**
     * 获取玩家
     * <p>
     * @param name 名字
     * @return ItemPlayer
     */
    public ItemPlayer getPlayer(String name)
    {
        for (ItemPlayer player : this.playerList)
        {
            if (player.getCraftPlayer() != null && player.getCraftPlayer().isOnline())
            {
                if (player.getCraftPlayer().getName().equalsIgnoreCase(name))
                {
                    return player;
                }
            }
        }

        return null;
    }

    /**
     * 添加玩家
     * <p>
     * @param player 玩家
     */
    public void addPlayer(ItemPlayer player)
    {
        if (this.getPlayer(player.getCraftPlayer().getUniqueId()) == null)
        {
            this.playerList.add(player);
        }
    }

    /**
     * 移除玩家
     * <p>
     * @param name 名字
     */
    public void removePlayer(String name)
    {
        for (int i = 0; i < this.playerList.size(); i++)
        {
            ItemPlayer player = this.playerList.get(i);

            if (player.getCraftPlayer().getName().equalsIgnoreCase(name))
            {
                this.playerList.remove(player);
                return;
            }
        }
    }

    /**
     * 移除玩家
     * <p>
     * @param uuid UniqueID
     */
    public void removePlayer(UUID uuid)
    {
        for (int i = 0; i < this.playerList.size(); i++)
        {
            ItemPlayer player = this.playerList.get(i);

            if (player.getCraftPlayer().getUniqueId().equals(uuid))
            {
                this.playerList.remove(player);
                return;
            }
        }
    }

    /**
     * 获取所有玩家
     * <p>
     * @return List<ItemPlayer>
     */
    public List<ItemPlayer> getPlayers()
    {
        return Collections.unmodifiableList(this.playerList);
    }
}

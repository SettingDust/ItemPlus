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

package com.ItemPlus.Core.v1_0_0.Entity;

import com.ItemPlus.ItemPlus;
import java.util.UUID;
import org.bukkit.entity.Player;

/**
 * ItemPlus玩家
 * <p>
 * @author HotFlow
 */
public class ItemPlayer extends ItemEntity
{
    private final UUID uuid;
    private Player player;

    /**
     * 构造ItemPlus玩家
     * <p>
     * @param uuid uuid
     * @param player 玩家
     */
    public ItemPlayer(UUID uuid, Player player)
    {
        super(player);
        this.uuid = uuid;
        this.player = player;
    }

    /**
     * 获取UUID
     * <p>
     * @return UUID
     */
    public UUID getUUID()
    {
        return this.uuid;
    }

    /**
     * 设置CraftPlayer
     * <p>
     * @param player 玩家
     */
    public void setCraftPlayer(Player player)
    {
        this.player = player;
    }

    /**
     * 获取CraftPlayer
     * <p>
     * @return Player
     */
    public Player getCraftPlayer()
    {
        return this.player;
    }

    /**
     * 发送信息
     * <p>
     * @param message 信息
     */
    public void sendMessage(String message)
    {
        this.player.sendMessage(ItemPlus.getPrefix() + message);
    }

    /**
     * 发送信息
     * <p>
     * @param messages 信息
     */
    public void sendMessage(String[] messages)
    {
        String[] newMessages = new String[messages.length];

        for (int i = 0; i < messages.length; i++)
        {
            newMessages[i] = ItemPlus.getPrefix() + messages[i];
        }

        this.player.sendMessage(newMessages);
    }

    /**
     * 发送原始信息
     * <p>
     * @param message 信息
     */
    public void sendRawMessage(String message)
    {
        this.player.sendRawMessage(ItemPlus.getPrefix() + message);
    }
}

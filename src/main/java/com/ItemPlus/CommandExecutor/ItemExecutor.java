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

package com.ItemPlus.CommandExecutor;

import org.bukkit.command.CommandSender;

/**
 * ItemPlus的命令执行器
 * <p>
 * @author HotFlow
 */
public interface ItemExecutor
{

    /**
     * 执行命令
     * <p>
     * @param sender 发送者
     * @param args   参数
     * @return Boolean
     */
    public Boolean execute(CommandSender sender, String[] args);
}

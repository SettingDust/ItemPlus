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

import com.ItemPlus.CommandExecutor.ItemExecutor;
import java.util.ArrayList;
import java.util.List;

/**
 * 命令管理器
 * <p>
 * @author HotFlow
 */
public final class CommandManager
{
    private final List<ItemExecutor> executors = new ArrayList<ItemExecutor>();

    /**
     * 获取命令执行器列表
     * <p>
     * @return List<ItemExecutor>
     */
    public List<ItemExecutor> getCommandExecutors()
    {
        return this.executors;
    }
}

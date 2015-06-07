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

package com.ItemPlus.Core.v0_0_1.Manager;

import java.util.logging.Logger;

/**
 * Logger管理器
 * <p>
 * @author HotFlow
 */
public final class LoggerManager
{
    /**
     * 获取Logger
     * <p>
     * @return Logger
     */
    public Logger getLogger()
    {
        return Logger.getLogger("ItemPlus");
    }

    /**
     * 获取Logger
     * <p>
     * @param name 名称
     * @return Logger
     */
    public Logger getLogger(String name)
    {
        return Logger.getLogger(name);
    }

    /**
     * 获取Logger
     * <p>
     * @param name 名称
     * @param resourceBundleName 资源包名称
     * @return Logger
     */
    public Logger getLogger(String name, String resourceBundleName)
    {
        return Logger.getLogger(name, resourceBundleName);
    }
}

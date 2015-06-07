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

package com.ItemPlus.Core.v0_0_1;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * 控制台消息处理器
 * <p>
 * @author HotFlow
 */
public abstract class LoggerHandler extends Handler
{
    @Override
    public abstract void publish(final LogRecord record);

    @Override
    public void flush()
    {

    }

    @Override
    public void close() throws SecurityException
    {

    }
}

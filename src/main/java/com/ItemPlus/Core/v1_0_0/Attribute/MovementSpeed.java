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

package com.ItemPlus.Core.v1_0_0.Attribute;

import com.ItemPlus.Item.Attribute.Attribute;
import java.util.UUID;

/**
 * 移动速度属性
 * <p>
 * @author HotFlow
 */
public class MovementSpeed extends Attribute
{
    /**
     * 构造移动速度属性
     * <p>
     * @param name      名字
     * @param operation 操作方式
     * @param amount    值
     * @param uuid      uuid
     */
    public MovementSpeed(String name, int operation, int amount, UUID uuid)
    {
        super(name, operation, amount, uuid);
    }

    /**
     * 构造移动速度属性
     * <p>
     * @param name  名字
     * @param level 等级
     */
    public MovementSpeed(String name, int level)
    {
        super(name, 1, level, UUID.randomUUID());
    }

    public String getTypeString()
    {
        return "generic.movementSpeed";
    }
}

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

package com.ItemPlus.NBT;

/**
 * NBT标签
 * <p>
 * @author HotFlow
 */
public abstract class TAG
{
    private final String name;

    public TAG(final String name)
    {
        this.name = name;
    }

    /**
     * 获取名称
     * <p>
     * @return String
     */
    public final String getName()
    {
        return name;
    }

    /**
     * 获取值
     * <p>
     * @return Object
     */
    public abstract Object getValue();

    /**
     * 设置值
     * <p>
     * @param value 值
     */
    public abstract void setValue(Object value);

    /**
     * 是否等于标签
     * <p>
     * @param tag 标签
     * @return Boolean
     */
    public Boolean equals(TAG tag)
    {
        if (tag != null)
        {
            if (this == tag)
            {
                return true;
            }

            if (this.name.equals(tag.getName()))
            {
                return true;
            }
        }

        return false;
    }
}

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

import java.util.List;

/**
 * NBT标签类型
 * <p>
 * @author HotFlow
 */
public class TAG_List extends TAG
{
    private final Class<? extends TAG> type;
    private List<TAG> value;

    /**
     * 构造TAG_List
     * <p>
     * @param name 名称
     * @param type 类型
     * @param value 值
     */
    public TAG_List(final String name, final Class<? extends TAG> type, final List<TAG> value)
    {
        super(name);
        this.type = type;
        this.value = value;
    }

    /**
     * 获取类型��
     * <p>
     * @return Class<? extends Tag>
     */
    public Class<? extends TAG> getType()
    {
        return type;
    }

    @Override
    public List<TAG> getValue()
    {
        return this.value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setValue(Object value)
    {
        this.value = (List<TAG>) value;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();

        sb.append("\r\n").append("{").append("\r\n");

        for (final TAG tag : value)
        {
            sb.append("  ").append(tag.toString().replaceAll("\r\n", "\r\n  ")).append("\r\n");
        }

        sb.append("}");

        return this.getClass().getSimpleName() + (this.getName() != null && !this.getName().equals("") ? "(\"" + this.getName() + "\")" : "") + ": " + value.size() + " entries of type " + type.getSimpleName() + sb.toString();
    }

    @Override
    public Boolean equals(TAG tag)
    {
        if (tag != null)
        {
            if (this == tag)
            {
                return true;
            }

            if (tag instanceof TAG_List)
            {
                if (this.getValue().equals(tag.getValue()))
                {
                    return true;
                }
            }
        }

        return false;
    }
}

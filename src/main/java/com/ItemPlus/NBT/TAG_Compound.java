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

import java.util.Collections;
import java.util.Map;

/**
 * NBT标签类型
 * <p>
 * @author HotFlow
 */
public class TAG_Compound extends TAG
{
    private Map<String, TAG> value;

    /**
     * 构造TAG_Compound
     * <p>
     * @param name 名称
     * @param value 值
     */
    public TAG_Compound(final String name, final Map<String, TAG> value)
    {
        super(name);
        this.value = value;
    }

    @Override
    public Map<String, TAG> getValue()
    {
        return this.value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setValue(Object value)
    {
        this.value = (Map<String, TAG>) value;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();

        sb.append("entries").append("\r\n");
        sb.append("{").append("\r\n");

        for (final Map.Entry<String, TAG> entry : value.entrySet())
        {
            sb.append("  ").append(entry.getValue().toString().replaceAll("\r\n", "\r\n  ")).append("\r\n");
        }

        sb.append("}");

        return this.getClass().getSimpleName() + (this.getName() != null && !this.getName().equals("") ? "(\"" + this.getName() + "\")" : "") + ": " + sb.toString();
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

            if (tag instanceof TAG_Compound)
            {
                if (this.getValue() == tag.getValue())
                {
                    return true;
                }
            }
        }

        return false;
    }
}

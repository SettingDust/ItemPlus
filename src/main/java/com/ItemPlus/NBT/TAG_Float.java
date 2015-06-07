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
 * NBT标签类型
 * <p>
 * @author HotFlow
 */
public class TAG_Float extends TAG
{
    private float value;

    /**
     * 构造TAG_Float
     * <p>
     * @param name 名称
     * @param value 值
     */
    public TAG_Float(final String name, final float value)
    {
        super(name);
        this.value = value;
    }

    @Override
    public Float getValue()
    {
        return this.value;
    }

    @Override
    public void setValue(Object value)
    {
        this.value = (Float) value;
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + (this.getName() != null && !this.getName().equals("") ? "(\"" + this.getName() + "\")" : "") + ": " + this.getValue();
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
            if (tag instanceof TAG_Float)
            {
                if (Float.floatToIntBits(this.getValue()) == Float.floatToIntBits(((Float) tag.getValue())))
                {
                    return true;
                }
            }
        }

        return false;
    }
}

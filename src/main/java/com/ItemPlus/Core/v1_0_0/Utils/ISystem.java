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

package com.ItemPlus.Core.v1_0_0.Utils;

import com.ItemPlus.NBT.TAG_Byte;
import com.ItemPlus.NBT.TAG_Byte_Array;
import com.ItemPlus.NBT.TAG_Compound;
import com.ItemPlus.NBT.TAG_Double;
import com.ItemPlus.NBT.TAG_End;
import com.ItemPlus.NBT.TAG_Float;
import com.ItemPlus.NBT.TAG_Int;
import com.ItemPlus.NBT.TAG_Int_Array;
import com.ItemPlus.NBT.TAG_List;
import com.ItemPlus.NBT.TAG_Long;
import com.ItemPlus.NBT.TAG_Short;
import com.ItemPlus.NBT.TAG_String;
import com.ItemPlus.NBT.TAG;
import java.math.BigDecimal;

/**
 * 常用功能
 * <p>
 * @author HotFlow
 */
public class ISystem
{
    /**
     * 是否为整数
     * <p>
     * @param s 字符串
     * @return Boolean
     */
    public static Boolean isInt(final String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
    }

    /**
     * 是否为数字
     * <p>
     * @param s 字符串
     * @return Boolean
     */
    public static Boolean isNum(final String s)
    {
        try
        {
            new BigDecimal(s);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    /**
     * 获取NBT类型Class
     * <p>
     * @param type Integer
     * @return Class<? extends Tag>
     */
    public static Class<? extends TAG> getTypeClass(final int type)
    {
        switch (type)
        {
            case 0:
                return TAG_End.class;
            case 1:
                return TAG_Byte.class;
            case 2:
                return TAG_Short.class;
            case 3:
                return TAG_Int.class;
            case 4:
                return TAG_Long.class;
            case 5:
                return TAG_Float.class;
            case 6:
                return TAG_Double.class;
            case 7:
                return TAG_Byte_Array.class;
            case 8:
                return TAG_String.class;
            case 9:
                return TAG_List.class;
            case 10:
                return TAG_Compound.class;
            case 11:
                return TAG_Int_Array.class;
            default:
                throw new IllegalArgumentException("NBT类型不存在!");
        }
    }

    /**
     * 获取NBT类型code
     * <p>
     * @param type Class<? extends Tag>
     * @return Integer
     */
    public static int getTypeCode(final Class<? extends TAG> type)
    {
        if (type.equals(TAG_End.class))
        {
            return 0;
        }
        else if (type.equals(TAG_Byte.class))
        {
            return 1;
        }
        else if (type.equals(TAG_Short.class))
        {
            return 2;
        }
        else if (type.equals(TAG_Int.class))
        {
            return 3;
        }
        else if (type.equals(TAG_Long.class))
        {
            return 4;
        }
        else if (type.equals(TAG_Float.class))
        {
            return 5;
        }
        else if (type.equals(TAG_Double.class))
        {
            return 6;
        }
        else if (type.equals(TAG_Byte_Array.class))
        {
            return 7;
        }
        else if (type.equals(TAG_String.class))
        {
            return 8;
        }
        else if (type.equals(TAG_List.class))
        {
            return 9;
        }
        else if (type.equals(TAG_Compound.class))
        {
            return 10;
        }
        else if (type.equals(TAG_Int_Array.class))
        {
            return 11;
        }
        else
        {
            throw new IllegalArgumentException("NBT类型不存在!");
        }
    }
}

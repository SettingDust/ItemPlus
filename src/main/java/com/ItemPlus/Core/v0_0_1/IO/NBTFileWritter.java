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

package com.ItemPlus.Core.v0_0_1.IO;

import com.ItemPlus.Core.v0_0_1.Utils.ISystem;
import com.ItemPlus.NBT.TAG_Byte;
import com.ItemPlus.NBT.TAG_Byte_Array;
import com.ItemPlus.NBT.TAG_Compound;
import com.ItemPlus.NBT.TAG_Double;
import com.ItemPlus.NBT.TAG_Float;
import com.ItemPlus.NBT.TAG_Int;
import com.ItemPlus.NBT.TAG_Int_Array;
import com.ItemPlus.NBT.TAG_List;
import com.ItemPlus.NBT.TAG_Long;
import com.ItemPlus.NBT.TAG_Short;
import com.ItemPlus.NBT.TAG_String;
import com.ItemPlus.NBT.TAG;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * NBT文件写存器
 * <p>
 * @author HotFlow
 */
public final class NBTFileWritter implements Closeable
{
    private final DataOutputStream os;

    /**
     * 构造NBTWritter
     * <p>
     * @param os 数据
     * @throws IOException
     */
    public NBTFileWritter(final OutputStream os) throws IOException
    {
        this.os = new DataOutputStream(new GZIPOutputStream(os));
    }

    /**
     * 构造NBTWritter
     * <p>
     * @param os 数据
     * @throws IOException
     */
    public NBTFileWritter(final DataOutputStream os) throws IOException
    {
        this.os = os;
    }

    /**
     * 写出标签
     * <p>
     * @param tag NBT标签
     * @throws IOException
     */
    public void write(final TAG tag) throws IOException
    {
        final int type = ISystem.getTypeCode(tag.getClass());
        final String name = tag.getName();
        final byte[] nameBytes = name.getBytes("UTF-8");

        os.writeByte(type);
        os.writeShort(nameBytes.length);
        os.write(nameBytes);

        if (type == 0)
        {
            throw new IOException("不能写出TAG_End");
        }

        this.writeTag(tag);
    }

    @Override
    public void close() throws IOException
    {
        this.os.close();
    }

    private void writeTag(final TAG tag) throws IOException
    {
        switch (ISystem.getTypeCode(tag.getClass()))
        {
            case 0:
                break;
            case 1:
                os.writeByte(((TAG_Byte) tag).getValue());
                break;
            case 2:
                os.writeShort(((TAG_Short) tag).getValue());
                break;
            case 3:
                os.writeInt(((TAG_Int) tag).getValue());
                break;
            case 4:
                os.writeLong(((TAG_Long) tag).getValue());
                break;
            case 5:
                os.writeFloat(((TAG_Float) tag).getValue());
                break;
            case 6:
                os.writeDouble(((TAG_Double) tag).getValue());
                break;
            case 7:
                byte[] bytes = ((TAG_Byte_Array) tag).getValue();
                os.writeInt(bytes.length);
                os.write(bytes);
                break;
            case 8:
                bytes = ((TAG_String) tag).getValue().getBytes("UTF-8");
                os.writeShort(bytes.length);
                os.write(bytes);
                break;
            case 9:
                final Class<? extends TAG> clazz = ((TAG_List) tag).getType();
                final List<TAG> tags = ((TAG_List) tag).getValue();
                final int size = tags.size();
                os.writeByte(ISystem.getTypeCode(clazz));
                os.writeInt(size);
                for (int i = 0; i < size; i++)
                {
                    this.writeTag(tags.get(i));
                }
                break;
            case 10:
                for (final TAG childTag : ((TAG_Compound) tag).getValue().values())
                {
                    this.write(childTag);
                }
                os.writeByte((byte) 0);
                break;
            case 11:
                final int[] ints = ((TAG_Int_Array) tag).getValue();
                os.writeInt(ints.length);
                for (int i = 0; i < ints.length; i++)
                {
                    os.writeInt(ints[i]);
                }
                break;
            default:
                throw new IOException("NBT类型不存在: " + tag.getClass().getSimpleName());
        }
    }
}

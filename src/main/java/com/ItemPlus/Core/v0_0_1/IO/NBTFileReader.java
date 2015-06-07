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
import com.ItemPlus.NBT.TAG_End;
import com.ItemPlus.NBT.TAG_Float;
import com.ItemPlus.NBT.TAG_Int;
import com.ItemPlus.NBT.TAG_Int_Array;
import com.ItemPlus.NBT.TAG_List;
import com.ItemPlus.NBT.TAG_Long;
import com.ItemPlus.NBT.TAG_Short;
import com.ItemPlus.NBT.TAG_String;
import com.ItemPlus.NBT.TAG;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * NBT文件读取器
 * <p>
 * @author HotFlow
 */
public final class NBTFileReader
{
    private final DataInputStream is;
    private final TAG tag;

    /**
     * 构造NBTReader
     * <p>
     * @param is 数据
     * @throws IOException
     */
    public NBTFileReader(final InputStream is) throws IOException
    {
        this.is = new DataInputStream(new GZIPInputStream(is));
        this.tag = this.readTag(0);
        this.is.close();
    }

    /**
     * 构造NBTReader
     * <p>
     * @param is 数据
     * @throws java.io.IOException
     */
    public NBTFileReader(final DataInputStream is) throws IOException
    {
        this.is = is;
        this.tag = this.readTag(0);
        this.is.close();
    }

    /**
     * 获取标签
     * <p>
     * @return Tag
     * @throws java.io.IOException
     */
    public TAG getTag() throws IOException
    {
        return this.tag;
    }

    private TAG readTag(int depth) throws IOException
    {
        final int type = is.readByte() & 0xFF;
        String name;

        if (type != 0)
        {
            final int length = is.readShort() & 0xFFFF;
            final byte[] bs = new byte[length];
            is.readFully(bs);
            name = new String(bs, Charset.forName("UTF-8"));
        }
        else
        {
            name = "";
        }

        return readTag(type, name, depth);
    }

    private TAG readTag(final int type, final String name, int depth) throws IOException
    {
        switch (type)
        {
            case 0:
                if (depth != 0)
                {
                    return new TAG_End();
                }
            case 1:
                return new TAG_Byte(name, is.readByte());
            case 2:
                return new TAG_Short(name, is.readShort());
            case 3:
                return new TAG_Int(name, is.readInt());
            case 4:
                return new TAG_Long(name, is.readLong());
            case 5:
                return new TAG_Float(name, is.readFloat());
            case 6:
                return new TAG_Double(name, is.readDouble());
            case 7:
                int len = is.readInt();
                byte[] bytes = new byte[len];
                is.readFully(bytes);
                return new TAG_Byte_Array(name, bytes);
            case 8:
                len = is.readShort();
                bytes = new byte[len];
                is.readFully(bytes);
                return new TAG_String(name, new String(bytes, Charset.forName("UTF-8")));
            case 9:
                final int childType = is.readByte();
                len = is.readInt();
                final List<TAG> tagList = new ArrayList<TAG>();

                for (int i = 0; i < len; i++)
                {
                    final TAG tag = readTag(childType, "", depth + 1);

                    if (tag instanceof TAG_End)
                    {
                        throw new IOException("TAG_End 不可存在于一个list内");
                    }
                    tagList.add(tag);
                }

                return new TAG_List(name, ISystem.getTypeClass(childType), tagList);
            case 10:
                final Map<String, TAG> tagMap = new HashMap<String, TAG>();
                while (true)
                {
                    final TAG tag = readTag(depth + 1);
                    if (tag instanceof TAG_End)
                    {
                        break;
                    }
                    else
                    {
                        tagMap.put(tag.getName(), tag);
                    }
                }
                return new TAG_Compound(name, tagMap);
            case 11:
                len = is.readInt();
                final int[] ints = new int[len];
                for (int i = 0; i < len; i++)
                {
                    ints[i] = is.readInt();
                }
                return new TAG_Int_Array(name, ints);
            default:
                throw new IOException("NBT类型不存在: " + type);
        }
    }
}

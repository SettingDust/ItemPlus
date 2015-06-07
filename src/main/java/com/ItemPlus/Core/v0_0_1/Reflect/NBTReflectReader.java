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

package com.ItemPlus.Core.v0_0_1.Reflect;

import com.ItemPlus.Core.v0_0_1.Utils.ISystem;
import com.ItemPlus.ItemPlus;
import com.ItemPlus.NBT.TAG;
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
import com.comphenix.protocol.utility.MinecraftReflection;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.inventory.ItemStack;

/**
 * NBT反射读取器
 * <p>
 * @author HotFlow
 */
public final class NBTReflectReader
{
    private Class<?> CRAFT_STACK;
    private Field CRAFT_HANDLE;
    private Field STACK_TAG;
    private final Reflector reflect = new Reflector();
    private Object nms;
    private final Object tag;
    private ItemStack item;
    
    /**
     * 构造NBT反射读取器
     * <p>
     * @param item 物品
     */
    public NBTReflectReader(final ItemStack item)
    {
        ClassLoader loader = NBTReflectReader.class.getClassLoader();
        
        this.item = MinecraftReflection.getBukkitItemStack(item);

        try
        {
            this.CRAFT_STACK = loader.loadClass(Reflector.getMinecraftPackageName() + ".inventory.CraftItemStack");
            this.CRAFT_HANDLE = this.reflect.getField(null, CRAFT_STACK, "handle");

            Class<?> clazz = CRAFT_HANDLE.getType();

            String tag = "tag";

            for (Field field : clazz.getDeclaredFields())
            {
                if (field.getName().equalsIgnoreCase("tag") || field.getName().equalsIgnoreCase("field_77990_d"))
                {
                    tag = field.getName();
                }
            }

            this.STACK_TAG = this.reflect.getField(null, CRAFT_HANDLE.getType(), tag);

        }
        catch (ClassNotFoundException ex)
        {
            ItemPlus.logger.getLogger().log(Level.SEVERE, null, ex);
        }

        this.nms = this.reflect.getFieldValue(CRAFT_HANDLE, this.item);
        this.tag = this.reflect.getFieldValue(STACK_TAG, nms);
    }

    /**
     * 构造NBT反射读取器
     * <p>
     * @param tag 标签
     */
    public NBTReflectReader(final Object tag)
    {
        this.tag = tag;
    }

    /**
     * 获取标签
     * <p>
     * @return TAG
     * @throws java.lang.Exception
     */
    public TAG getTag() throws Exception
    {
        return getTag("", tag);
    }

    @SuppressWarnings("unchecked")
    private TAG getTag(final String name, final Object value) throws Exception
    {
        if (value instanceof Void)
        {
            return new TAG_End();
        }
        else if (value.getClass().getName().contains("NBTTagByte"))
        {
            return new TAG_Byte(name, new Byte(value.toString()));
        }
        else if (value.getClass().getName().contains("NBTTagShort"))
        {
            return new TAG_Short(name, new Short(value.toString()));
        }
        else if (value.getClass().getName().contains("NBTTagInt"))
        {
            return new TAG_Int(name, new Integer(value.toString()));
        }
        else if (value.getClass().getName().contains("NBTTagLong"))
        {
            return new TAG_Long(name, new Long(value.toString().substring(0, value.toString().length() - 1)));
        }
        else if (value.getClass().getName().contains("NBTTagFloat"))
        {
            return new TAG_Float(name, new Float(value.toString()));
        }
        else if (value.getClass().getName().contains("NBTTagDouble"))
        {
            return new TAG_Double(name, new Double(value.toString().substring(0, value.toString().length() - 1)));
        }
        else if (value.getClass().getName().contains("NBTTagByteArray"))
        {
            List<Byte> bs = new ArrayList<Byte>();

            for (int i = 0; i < Array.getLength(value); i++)
            {
                bs.add((Byte) Array.get(value, i));
            }

            byte[] bytes = new byte[bs.size()];

            for (int i = 0; i < bs.size(); i++)
            {
                bytes[i] = bs.get(i);
            }

            return new TAG_Byte_Array(name, bytes);
        }
        else if (value.getClass().getName().contains("NBTTagString"))
        {
            return new TAG_String(name, value.toString().replace("\"\"", "\""));
        }
        else if (value.getClass().getName().contains("NBTTagList"))
        {
            final List<TAG> tagList = new ArrayList<TAG>();

            String type = "type";

            for (Field field : value.getClass().getDeclaredFields())
            {
                if (field.getName().equalsIgnoreCase("type") || field.getName().equalsIgnoreCase("field_74746_b"))
                {
                    type = field.getName();
                }
            }

            for (int i = 0; i < getDataList(value).size(); i++)
            {
                final TAG tag = getTag("", getDataList(value).get(i));

                if (tag instanceof TAG_End)
                {
                    throw new Exception("TAG_End 不可存在于一个list内");
                }

                tagList.add(tag);
            }

            return new TAG_List(name, ISystem.getTypeClass((Byte) reflect.getFieldValue(getDataField(value, type), value)), tagList);
        }
        else if (value.getClass().getName().contains("NBTTagCompound"))
        {
            final Map<String, TAG> tagMap = new HashMap<String, TAG>();

            for (String key : getDataMap(value).keySet())
            {
                tagMap.put(key, getTag(key, getDataMap(value).get(key)));
            }

            return new TAG_Compound(name, tagMap);
        }
        else if (value.getClass().getName().contains("NBTTagIntArray"))
        {
            List<Integer> ins = new ArrayList<Integer>();

            for (int i = 0; i < Array.getLength(value); i++)
            {
                ins.add((Integer) Array.get(value, i));
            }

            int[] ints = new int[ins.size()];

            for (int i = 0; i < ins.size(); i++)
            {
                ints[i] = ins.get(i);
            }

            return new TAG_Int_Array(name, ints);
        }
        else
        {
            throw new Exception("NBT类型 " + value.getClass().getName() + " 不存在!");
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getDataMap(final Object handle)
    {
        String map = "map";

        for (Field field : handle.getClass().getDeclaredFields())
        {
            if (field.getName().equalsIgnoreCase("map") || field.getName().equalsIgnoreCase("field_74784_a"))
            {
                map = field.getName();
            }
        }

        return (Map<String, Object>) reflect.getFieldValue(getDataField(handle, map), handle);
    }

    @SuppressWarnings("unchecked")
    private List<Object> getDataList(final Object handle)
    {
        String list = "list";

        for (Field field : handle.getClass().getDeclaredFields())
        {
            if (field.getName().equalsIgnoreCase("list") || field.getName().equalsIgnoreCase("field_74747_a"))
            {
                list = field.getName();
            }
        }

        return (List<Object>) reflect.getFieldValue(getDataField(handle, list), handle);
    }

    private Field getDataField(final Object nms, final String fieldName)
    {
        return reflect.getField(nms, null, fieldName);
    }
}

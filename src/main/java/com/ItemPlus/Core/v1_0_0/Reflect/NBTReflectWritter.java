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

package com.ItemPlus.Core.v1_0_0.Reflect;

import com.ItemPlus.Core.v1_0_0.Utils.ISystem;
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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.inventory.ItemStack;

/**
 * NBT反射写存器
 * <p>
 * @author HotFlow
 */
public final class NBTReflectWritter
{
    private Class<?> CRAFT_STACK;
    private Field CRAFT_HANDLE;
    private Field STACK_TAG;
    private final Reflector reflect = new Reflector();
    private Object nms;
    private Object tag;
    private ItemStack item;

    /**
     * 构造NBT反射写存器
     * <p>
     * @param item 物品
     */
    public NBTReflectWritter(final ItemStack item)
    {
        ClassLoader loader = NBTReflectReader.class.getClassLoader();

        try
        {
            this.item = item;
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
     * 构造NBT反射写存器
     * <p>
     * @param tag 标签
     */
    public NBTReflectWritter(final Object tag)
    {
        this.tag = tag;
    }

    /**
     * 写入标签
     * <p>
     * @param tag 标签
     * @throws java.lang.Exception
     */
    public void writeTag(final TAG tag) throws Exception
    {
        this.tag = buildTag(tag);

        if (this.item != null)
        {
            this.reflect.setFieldValue(this.STACK_TAG, this.nms, this.tag);
        }
    }

    private Object buildTag(final TAG tag) throws Exception
    {
        Object target = getTag(tag);

        if (tag instanceof TAG_List)
        {
            String type = "type";

            for (Field field : target.getClass().getDeclaredFields())
            {
                if (field.getName().equalsIgnoreCase("type") || field.getName().equalsIgnoreCase("field_74746_b"))
                {
                    type = field.getName();
                }
            }

            this.reflect.setFieldValue(this.reflect.getField(target, null, type), target, (byte) ISystem.getTypeCode(((TAG_List) tag).getType()));

            List<Object> value = new ArrayList<Object>();
            for (TAG t : ((TAG_List) tag).getValue())
            {
                value.add(buildTag(t));
            }

            this.reflect.setFieldValue(getDataField(target, tag), target, value);
        }
        else if (tag instanceof TAG_Compound)
        {
            Map<String, Object> value = new HashMap<String, Object>();

            for (String key : ((TAG_Compound) tag).getValue().keySet())
            {
                value.put(key, buildTag(((TAG_Compound) tag).getValue().get(key)));
            }

            this.reflect.setFieldValue(getDataField(target, tag), target, value);
        }

        return target;
    }

    private Object getTag(TAG tag) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        String packageName = "net.minecraft.nbt.";

        try
        {
            Class.forName(packageName + "NBTBase");
        }
        catch (ClassNotFoundException ex)
        {
            packageName = "net.minecraft.server.v1_7_R4.";
        }

        if (tag instanceof TAG_End)
        {
            return Class.forName(packageName + "NBTTagEnd").getConstructor().newInstance();
        }
        else if (tag instanceof TAG_Byte)
        {
            return Class.forName(packageName + "NBTTagByte").getConstructor(byte.class).newInstance(tag.getValue());
        }
        else if (tag instanceof TAG_Short)
        {
            return Class.forName(packageName + "NBTTagShort").getConstructor(short.class).newInstance(tag.getValue());
        }
        else if (tag instanceof TAG_Int)
        {
            return Class.forName(packageName + "NBTTagInt").getConstructor(int.class).newInstance(tag.getValue());
        }
        else if (tag instanceof TAG_Long)
        {
            return Class.forName(packageName + "NBTTagLong").getConstructor(long.class).newInstance(tag.getValue());
        }
        else if (tag instanceof TAG_Float)
        {
            return Class.forName(packageName + "NBTTagFloat").getConstructor(float.class).newInstance(tag.getValue());
        }
        else if (tag instanceof TAG_Double)
        {
            return Class.forName(packageName + "NBTTagDouble").getConstructor(double.class).newInstance(tag.getValue());
        }
        else if (tag instanceof TAG_Byte_Array)
        {
            return Class.forName(packageName + "NBTTagByteArray").getConstructor(byte[].class).newInstance(tag.getValue());
        }
        else if (tag instanceof TAG_String)
        {
            return Class.forName(packageName + "NBTTagString").getConstructor(String.class).newInstance(((TAG_String) tag).getValue().replace("\"\"", "\""));
        }
        else if (tag instanceof TAG_List)
        {
            return Class.forName(packageName + "NBTTagList").getConstructor().newInstance();
        }
        else if (tag instanceof TAG_Compound)
        {
            return Class.forName(packageName + "NBTTagCompound").getConstructor().newInstance();
        }
        else if (tag instanceof TAG_Int_Array)
        {
            return Class.forName(packageName + "NBTTagIntArray").getConstructor(int[].class).newInstance(tag.getValue());
        }

        return null;
    }

    private Field getDataField(final Object nms, final TAG tag)
    {
        if (tag instanceof TAG_Compound)
        {
            String map = "map";

            for (Field field : nms.getClass().getDeclaredFields())
            {
                if (field.getName().equalsIgnoreCase("map") || field.getName().equalsIgnoreCase("field_74784_a"))
                {
                    map = field.getName();
                }
            }

            return reflect.getField(nms, null, map);
        }
        else if (tag instanceof TAG_List)
        {
            String list = "list";

            for (Field field : nms.getClass().getDeclaredFields())
            {
                if (field.getName().equalsIgnoreCase("list") || field.getName().equalsIgnoreCase("field_74747_a"))
                {
                    list = field.getName();
                }
            }

            return reflect.getField(nms, null, list);
        }
        else
        {
            return reflect.getField(nms, null, "data");
        }
    }
}

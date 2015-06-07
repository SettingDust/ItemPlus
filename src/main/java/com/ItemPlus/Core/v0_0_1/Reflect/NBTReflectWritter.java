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
import java.lang.reflect.Field;
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
    private final Object tag;

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

        Object nms = this.reflect.getFieldValue(CRAFT_HANDLE, MinecraftReflection.getBukkitItemStack(item));
        Object tag = this.reflect.getFieldValue(STACK_TAG, nms);

        this.tag = tag;
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
        this.writeTag(tag, this.tag);
    }

    private void writeTag(final TAG tag, final Object target) throws Exception
    {
        if (tag instanceof TAG_End)
        {
            return;
        }
        else if (tag instanceof TAG_Byte)
        {
            Class.forName("net.minecraft.nbt.NBTTagByte").getConstructor(Byte.class).newInstance(tag.getValue());
            
            this.reflect.setFieldValue(getDataField(target, tag), target, tag.getValue());
            return;
        }
        else if (tag instanceof TAG_Short)
        {
            this.reflect.setFieldValue(getDataField(target, tag), target, tag.getValue());
            return;
        }
        else if (tag instanceof TAG_Int)
        {
            this.reflect.setFieldValue(getDataField(target, tag), target, tag.getValue());
            return;
        }
        else if (tag instanceof TAG_Long)
        {
            this.reflect.setFieldValue(getDataField(target, tag), target, tag.getValue());
            return;
        }
        else if (tag instanceof TAG_Float)
        {
            this.reflect.setFieldValue(getDataField(target, tag), target, tag.getValue());
            return;
        }
        else if (tag instanceof TAG_Double)
        {
            this.reflect.setFieldValue(getDataField(target, tag), target, tag.getValue());
            return;
        }
        else if (tag instanceof TAG_Byte_Array)
        {
            this.reflect.setFieldValue(getDataField(target, tag), target, tag.getValue());
            return;
        }
        else if (tag instanceof TAG_String)
        {
            this.reflect.setFieldValue(getDataField(target, tag), target, tag.getValue());
            return;
        }
        else if (tag instanceof TAG_List)
        {
            String type = "type";

            for (Field field : target.getClass().getDeclaredFields())
            {
                if (field.getName().equalsIgnoreCase("type") || field.getName().equalsIgnoreCase("field_74746_b"))
                {
                    type = field.getName();
                }
            }

            this.reflect.setFieldValue(this.reflect.getField(target, null, type), target, ISystem.getTypeCode(((TAG_List) tag).getType()));

            for (TAG t : ((TAG_List) tag).getValue())
            {
                this.writeTag(t, target);
            }
            return;
        }
        else if (tag instanceof TAG_Compound)
        {
            
            for (String key : ((TAG_Compound) tag).getValue().keySet())
            {
                this.writeTag(((TAG_Compound) tag).getValue().get(key), target);
            }

            this.writeTag(new TAG_End(), target);
            return;
        }
        else if (tag instanceof TAG_Int_Array)
        {
            this.reflect.setFieldValue(getDataField(target, tag), target, tag.getValue());
            return;
        }

        throw new Exception("NBT类型 " + tag.getClass().getName() + " 不存在!");
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

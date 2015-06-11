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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.Server;

/**
 * Minecraft反射工具
 * <p>
 * @author HotFlow
 */
public final class Reflector
{
    /**
     * 获取字段
     * <p>
     * @param instance 实例
     * @param clazz 类
     * @param fieldName 字段名
     * @return Field
     */
    public Field getField(Object instance, Class<?> clazz, String fieldName)
    {
        if (clazz == null)
        {
            clazz = instance.getClass();
        }

        for (Field field : clazz.getDeclaredFields())
        {
            if (field.getName().equals(fieldName))
            {
                field.setAccessible(true);
                return field;
            }
        }

        if (clazz.getSuperclass() != null)
        {
            return getField(instance, clazz.getSuperclass(), fieldName);
        }

        throw new IllegalStateException("Unable to find field " + fieldName + " in " + instance);
    }

    /**
     * 执行方法
     * <p>
     * @param method 方法
     * @param target 目标
     * @param params 参数
     * @return
     */
    public Object invokeMethod(Method method, Object target, Object... params)
    {
        try
        {
            return method.invoke(target, params);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException("Unable to invoke method " + method + " for " + target, e);
        }
        catch (IllegalArgumentException e)
        {
            throw new RuntimeException("Unable to invoke method " + method + " for " + target, e);
        }
        catch (InvocationTargetException e)
        {
            throw new RuntimeException("Unable to invoke method " + method + " for " + target, e);
        }
    }

    /**
     * 获取方法
     * <p>
     * @param requireMod 需要操作类型
     * @param bannedMod 禁止操作类型
     * @param clazz 类
     * @param methodName 方法名
     * @param params 参数
     * @return
     */
    public Method getMethod(int requireMod, int bannedMod, Class<?> clazz, String methodName, Class<?>... params)
    {
        for (Method method : clazz.getDeclaredMethods())
        {
            if ((method.getModifiers() & requireMod) == requireMod
                    && (method.getModifiers() & bannedMod) == 0
                    && (methodName == null || method.getName().equals(methodName))
                    && Arrays.equals(method.getParameterTypes(), params))
            {

                method.setAccessible(true);
                return method;
            }
        }

        if (clazz.getSuperclass() != null)
        {
            return getMethod(requireMod, bannedMod, clazz.getSuperclass(), methodName, params);
        }
        throw new IllegalStateException(String.format("Unable to find method %s (%s).", methodName, Arrays.asList(params)));
    }

    /**
     * 设置字段值
     * <p>
     * @param field 字段
     * @param target 实例
     * @param value 值
     */
    public void setFieldValue(Field field, Object target, Object value)
    {
        try
        {
            field.set(target, value);
        }
        catch (IllegalArgumentException e)
        {
            throw new RuntimeException("Unable to set " + field + " for " + target, e);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException("Unable to set " + field + " for " + target, e);
        }
    }

    /**
     * 获取字段值
     * <p>
     * @param field 字段
     * @param target 实例
     * @return Object
     */
    public Object getFieldValue(Field field, Object target)
    {
        try
        {
            return field.get(target);
        }
        catch (IllegalArgumentException e)
        {
            throw new RuntimeException("Unable to retrieve " + field + " for " + target, e);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException("Unable to retrieve " + field + " for " + target, e);
        }
    }

    /**
     * 获取包名
     * <p>
     * @return String
     */
    public static String getMinecraftPackageName()
    {
        Server server = Bukkit.getServer();
        String name = server != null ? server.getClass().getPackage().getName() : null;

        if (name != null && name.contains("craftbukkit"))
        {
            return name;
        }
        else
        {
            return "org.bukkit.craftbukkit.v1_7_R4";
        }
    }
}

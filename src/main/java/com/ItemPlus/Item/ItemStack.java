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

package com.ItemPlus.Item;

import com.ItemPlus.Core.v0_0_1.Reflect.NBTReflectReader;
import com.ItemPlus.Core.v0_0_1.Reflect.NBTReflectWritter;
import com.ItemPlus.ItemPlus;
import com.ItemPlus.NBT.TAG;
import com.ItemPlus.NBT.TAG_Compound;
import java.util.logging.Level;

/**
 * 单个物品
 * <p>
 * @author HotFlow
 */
public class ItemStack
{
    private final org.bukkit.inventory.ItemStack item;

    /**
     * 构造物品
     * <p>
     * @param item 物品
     */
    public ItemStack(org.bukkit.inventory.ItemStack item)
    {
        this.item = item;
    }

    /**
     * 获取Bukkit ItemStack
     * <p>
     * @return org.bukkit.inventory.ItemStack
     */
    public org.bukkit.inventory.ItemStack getCraftItemStack()
    {
        return this.item;
    }

    /**
     * 获取标签
     * <p>
     * @return TAG
     */
    public TAG getTag()
    {
        try
        {
            return new NBTReflectReader(this.item).getTag();
        }
        catch (Exception ex)
        {
            ItemPlus.logger.getLogger().log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * 设置标签
     * <p>
     * @param tag 标签
     */
    public void setTag(TAG_Compound tag)
    {
        try
        {
            new NBTReflectWritter(this.item).writeTag(tag);
        }
        catch (Exception ex)
        {
            ItemPlus.logger.getLogger().log(Level.SEVERE, null, ex);
        }
    }
}

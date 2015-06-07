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

package com.ItemPlus.Item.Attribute;

import java.util.UUID;

/**
 * 抽象属性
 * <p>
 * @author HotFlow
 */
public abstract class Attribute
{
    private String name;
    private int operation;
    private double amount;
    private UUID uuid;

    /**
     * 构造抽象属性
     * <p>
     * @param name 名字
     * @param operation 操作方式
     * @param amount 值
     * @param uuid uuid
     */
    public Attribute(String name, int operation, double amount, UUID uuid)
    {
        this.name = name;
        this.operation = operation;
        this.amount = amount;
        this.uuid = uuid;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getOperation()
    {
        return this.operation;
    }

    public void setOperation(int operation)
    {
        this.operation = operation;
    }

    public Double getAmount()
    {
        return this.amount;
    }

    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }

    public UUID getUUID()
    {
        return this.uuid;
    }

    public void setUUID(UUID id)
    {
        this.uuid = id;
    }

    public Attribute getType()
    {
        return this;
    }

    public abstract String getTypeString();
}

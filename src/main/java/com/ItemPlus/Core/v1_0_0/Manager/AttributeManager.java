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

package com.ItemPlus.Core.v1_0_0.Manager;

import com.ItemPlus.Item.Attribute.Attribute;
import java.util.HashMap;

/**
 * 属性管理器
 * <p>
 * @author HotFlow
 */
public final class AttributeManager
{
    private final HashMap<String, Class<? extends Attribute>> attributes = new HashMap<String, Class<? extends Attribute>>();

    /**
     * 获取全部属性
     * <p>
     * @return HashMap<String, Class<? extends Attribute>>
     */
    public HashMap<String, Class<? extends Attribute>> getAttributes()
    {
        return this.attributes;
    }
}

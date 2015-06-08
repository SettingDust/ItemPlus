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

import com.ItemPlus.Item.ItemStack;
import com.ItemPlus.NBT.TAG;
import com.ItemPlus.NBT.TAG_Compound;
import com.ItemPlus.NBT.TAG_Double;
import com.ItemPlus.NBT.TAG_Int;
import com.ItemPlus.NBT.TAG_List;
import com.ItemPlus.NBT.TAG_Long;
import com.ItemPlus.NBT.TAG_String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 物品属性储存器
 * <p>
 * @author HotFlow
 */
public final class AttributeStorage
{

    private final ItemStack item;
    private final List<Attribute> attributeList = new ArrayList<Attribute>();

    /**
     * 构造物品属性储存器
     * <p>
     * @param item 物品
     */
    public AttributeStorage(ItemStack item)
    {
        this.item = item;
        this.load();
    }

    /**
     * 保存属性
     */
    @SuppressWarnings("unchecked")
    public void save()
    {
        if (item.getTag() instanceof TAG_Compound)
        {
            TAG_Compound tag = (TAG_Compound) item.getTag();

            if (!tag.getValue().containsKey("AttributeModifiers"))
            {
                tag.getValue().put("AttributeModifiers", new TAG_List("AttributeModifiers", TAG_Compound.class, new ArrayList<TAG>()));
            }

            List<TAG> attributes = ((TAG_List) tag.getValue().get("AttributeModifiers")).getValue();

            Attributes:
            for (Attribute attribute : this.getAttributes())
            {
                for (TAG attribute1 : attributes)
                {
                    if (attribute1 instanceof TAG_Compound)
                    {
                        if (!((TAG_Compound) attribute1).getValue().containsKey("Name"))
                        {
                            continue Attributes;
                        }

                        String name = ((TAG_String) ((TAG_Compound) attribute1).getValue().get("Name")).getValue();

                        if (name.equals(attribute.getName()))
                        {
                            continue Attributes;
                        }
                    }
                    else
                    {
                        continue;
                    }
                }

                Map<String, TAG> map = new HashMap<String, TAG>();
                map.put("Name", new TAG_String("Name", attribute.getName()));
                map.put("AttributeName", new TAG_String("AttributeName", attribute.getTypeString()));
                map.put("Amount", new TAG_Double("Amount", attribute.getAmount()));
                map.put("Operation", new TAG_Int("Operation", attribute.getOperation()));
                map.put("UUIDMost", new TAG_Long("UUIDMost", attribute.getUUID().getMostSignificantBits()));
                map.put("UUIDLeast", new TAG_Long("UUIDLeast", attribute.getUUID().getLeastSignificantBits()));

                attributes.add(new TAG_Compound("", map));
            }

            this.item.setTag(tag);
        }
    }

    /**
     * 载入属性
     */
    public void load()
    {
        if (item.getTag() instanceof TAG_Compound)
        {
            TAG_Compound tag = (TAG_Compound) item.getTag();

            if (tag == null)
            {
                return;
            }

            if (!tag.getValue().containsKey("AttributeModifiers"))
            {
                tag.getValue().put("AttributeModifiers", new TAG_List("AttributeModifiers", TAG_Compound.class, new ArrayList<TAG>()));
            }

            List<TAG> attributes = ((TAG_List) tag.getValue().get("AttributeModifiers")).getValue();

            for (int i = 0; i < attributes.size(); i++)
            {
                if (attributes.get(i) instanceof TAG_Compound)
                {
                    TAG_Compound attribute = (TAG_Compound) attributes.get(i);

                    final String name = ((TAG_String) attribute.getValue().get("Name")).getValue();
                    final String attributeName = ((TAG_String) attribute.getValue().get("AttributeName")).getValue();
                    int operation = ((TAG_Int) attribute.getValue().get("Operation")).getValue();
                    Double amount = ((TAG_Double) attribute.getValue().get("Amount")).getValue();
                    UUID uuid = new UUID(((TAG_Long) attribute.getValue().get("UUIDMost")).getValue(), ((TAG_Long) attribute.getValue().get("UUIDLeast")).getValue());

                    Attribute abstractAttribute = new Attribute(name, operation, amount, uuid)
                    {
                        @Override
                        public String getTypeString()
                        {
                            return attributeName;
                        }

                    };

                    this.attributeList.add(abstractAttribute);
                }
            }
        }
    }

    /**
     * 获取属性
     * <p>
     * @param name 名称
     * @return Attribute
     */
    public Attribute getAttribute(String name)
    {
        for (Attribute attribute : this.getAttributes())
        {
            if (attribute.getName().equals(name))
            {
                return attribute;
            }
        }

        return null;
    }

    /**
     * 获取所有属性
     * <p>
     * @return List<Attribute>
     */
    public List<Attribute> getAttributes()
    {
        return this.attributeList;
    }

}

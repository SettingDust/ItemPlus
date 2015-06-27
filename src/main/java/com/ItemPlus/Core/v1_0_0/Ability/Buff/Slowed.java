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

package com.ItemPlus.Core.v1_0_0.Ability.Buff;

import com.ItemPlus.Item.Ability.Buff.Debuff;
import org.bukkit.entity.Entity;

/**
 * 被慢速
 * <p>
 * 目标移动速度被降低了。
 * <p>
 * @author HotFlow
 */
public class Slowed implements Debuff
{
    private final Entity entity;
    private final long time;
    private Boolean canBeRemove;

    public Slowed(Entity entity, long time, Boolean canBeRemove)
    {
        this.entity = entity;
        this.time = time;
        this.canBeRemove = canBeRemove;
    }

    @Override
    public Boolean canBeRemove()
    {
        return this.canBeRemove;
    }

    @Override
    public void setCanRemove(Boolean bln)
    {
        this.canBeRemove = bln;
    }

    @Override
    public Entity getEntity()
    {
        return this.entity;
    }

    @Override
    public long getTime()
    {
        return this.time;
    }

}

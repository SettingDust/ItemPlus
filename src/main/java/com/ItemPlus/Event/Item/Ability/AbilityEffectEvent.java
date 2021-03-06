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

package com.ItemPlus.Event.Item.Ability;

import com.ItemPlus.Item.Ability.Ability;
import org.bukkit.event.Cancellable;

/**
 * 技能发生效果事件
 * <p>
 * @author HotFlow
 */
public class AbilityEffectEvent extends AbilityEvent implements Cancellable
{
    private Boolean cancelled = false;

    /**
     * 构造技能发生效果事件
     * <p>
     * @param ability 技能
     */
    public AbilityEffectEvent(Ability ability)
    {
        super(ability);
    }

    @Override
    public boolean isCancelled()
    {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean bln)
    {
        this.cancelled = bln;
    }
}

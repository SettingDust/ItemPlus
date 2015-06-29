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

package com.ItemPlus.Core.v1_0_0.Ability.Buffs;

import com.ItemPlus.Item.Ability.Buff.BuffInfo;
import com.ItemPlus.Item.Ability.Buff.Debuff;

/**
 * 被慢速
 * <p>
 * 目标移动速度被降低了。
 * <p>
 * @author HotFlow
 */
public class Slowed extends Debuff
{

    /**
     * 构造被慢速
     * <p>
     * @param info 信息
     */
    public Slowed(BuffInfo info)
    {
        super(info);
    }

}

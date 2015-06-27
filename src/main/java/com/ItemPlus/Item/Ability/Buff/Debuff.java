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

package com.ItemPlus.Item.Ability.Buff;

/**
 * 负面状态
 * <p>
 * @author HotFlow
 */
public interface Debuff extends Buff
{
    /**
     * 是否可移除
     * <p>
     * @return Boolean
     */
    public Boolean canBeRemove();

    /**
     * 设置是否可以移除
     * <p>
     * @param bln 是否可被移除
     */
    public void setCanRemove(Boolean bln);
}

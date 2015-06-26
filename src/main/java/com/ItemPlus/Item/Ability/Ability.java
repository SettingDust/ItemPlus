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

package com.ItemPlus.Item.Ability;

import com.ItemPlus.ItemPlus;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.bukkit.event.block.Action;

/**
 * 抽象技能
 * <p>
 * @author HotFlow
 */
public abstract class Ability
{
    private final List<Action> actions;
    private final long cooldown;
    private final int durabilityCast;
    private final UUID uuid;

    /**
     * 构造技能
     * <p>
     * @param actions 执行方法
     * @param cooldown 冷却
     * @param durabilityCast 耐久消耗
     */
    public Ability(List<Action> actions, long cooldown, int durabilityCast)
    {
        this.actions = actions;
        this.cooldown = cooldown;
        this.durabilityCast = durabilityCast;
        this.uuid = UUID.randomUUID();
        ItemPlus.getAbilityManager().getAbilityMap().put(this.uuid, this);
    }

    /**
     * 获取执行方法
     * <p>
     * @return List<Action>
     */
    public List<Action> getActions()
    {
        return Collections.unmodifiableList(this.actions);
    }

    /**
     * 获取冷却时间
     * <p>
     * @return long
     */
    public long getCooldown()
    {
        return this.cooldown;
    }

    /**
     * 获取耐久消耗
     * <p>
     * @return int
     */
    public int getDurabilityCast()
    {
        return this.durabilityCast;
    }

    /**
     * 获取UUID
     * <p>
     * @return UUID
     */
    public UUID getUniqueID()
    {
        return this.uuid;
    }

    /**
     * 删除技能(技能结束后必须呼出)
     */
    public void delete()
    {
        ItemPlus.getAbilityManager().getAbilityMap().remove(this.uuid);
    }

    /**
     * 当发出技能时
     * <p>
     * @param info 技能信息
     */
    public abstract void onAbility(AbilityInfo info);
}

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

package com.ItemPlus.Core.v1_0_0.Ability;

import com.ItemPlus.Event.Item.Ability.AbilityEndedEvent;
import com.ItemPlus.Event.Item.Ability.AbilitySpellEvent;
import com.ItemPlus.Item.Ability.Ability;
import com.ItemPlus.Item.Ability.AbilityInfo;
import com.ItemPlus.ItemPlus;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.entity.LightningStrike;

/**
 * 雷击术
 * <p>
 * 对目标点造成一道雷击伤害。
 * <p>
 * @author HotFlow
 */
public class Shock extends Ability
{
    private final double damage;
    private LightningStrike lightning;

    /**
     * 构造雷击术
     * <p>
     * @param info 信息
     * @param damage 伤害
     */
    public Shock(double damage, AbilityInfo info)
    {
        super(info);
        this.damage = damage;
    }

    @Override
    public void onSpell()
    {
        if (this.getAbilityInfo().getLocation() != null)
        {
            AbilitySpellEvent event = new AbilitySpellEvent(this);
            getServer().getPluginManager().callEvent(event);

            if (event.isCancelled())
            {
                return;
            }

            ItemPlus.getAbilityManager().getAbilityMap().put(this.getUniqueId(), this);

            this.lightning = this.getAbilityInfo().getPlayer().getWorld().strikeLightning(this.getAbilityInfo().getLocation());
        }

    }

    @Override
    public void onEnded()
    {
        ItemPlus.getAbilityManager().getAbilityMap().remove(this.getUniqueId());

        AbilityEndedEvent event = new AbilityEndedEvent(this);
        getServer().getPluginManager().callEvent(event);
    }

    /**
     * 获取伤害
     * <p>
     * @return double
     */
    public double getDamage()
    {
        return this.damage;
    }

    /**
     * 获取雷电
     * <p>
     * @return LightningStrike
     */
    public LightningStrike getLightningStrike()
    {
        return this.lightning;
    }

}

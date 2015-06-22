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

import com.ItemPlus.Item.Ability.Ability;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

/**
 * 火球术，向前方发射一个火球
 * <p>
 * @author HotFlow
 */
public class FireBall implements Ability
{
    private final Player player;
    private final Location to;
    private final double damage;
    private final List<Action> actions;
    private final long cooldown;
    private final int durabilityCast;

    /**
     * 构造火球术
     * <p>
     * @param player 玩家
     * @param to 目标点
     * @param damage 伤害
     * @param actions 执行方法
     * @param cooldown 冷却
     * @param durabilityCast 耐久消耗
     */
    public FireBall(Player player, Location to, double damage, List<Action> actions, long cooldown, int durabilityCast)
    {
        this.player = player;
        this.to = to;
        this.damage = damage;
        this.actions = actions;
        this.cooldown = cooldown;
        this.durabilityCast = durabilityCast;
    }

    @Override
    public List<Action> getActions()
    {
        return this.actions;
    }

    @Override
    public long getCooldown()
    {
        return this.cooldown;
    }

    @Override
    public int getDurabilityCast()
    {
        return this.durabilityCast;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void run()
    {
        Location from = player.getLocation();
        from.setY(from.getY() + 1);
        Fireball ball = player.getWorld().spawn(from, Fireball.class);
        ball.setShooter(player);
        ball.setDirection(from.subtract(to).getDirection());
    }

}

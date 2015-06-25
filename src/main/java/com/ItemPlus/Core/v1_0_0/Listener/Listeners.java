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

package com.ItemPlus.Core.v1_0_0.Listener;

import com.ItemPlus.Core.v1_0_0.Ability.FireBall;
import com.ItemPlus.Item.Ability.Ability;
import com.ItemPlus.Item.Ability.AbilityInfo;
import com.ItemPlus.Item.Ability.AbilityType;
import com.ItemPlus.ItemPlus;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * 监听器
 * <p>
 * @author HotFlow
 */
public class Listeners implements Listener
{
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        List actions = new ArrayList<Action>();
        actions.add(event.getAction());
        
        FireBall ball = new FireBall(10, actions, 10L, 10);
        
        for (Action action : ball.getActions())
        {
            if (event.getAction() == action)
            {
                try
                {
                    ball.onAbility(new AbilityInfo(AbilityType.Point, event.getPlayer(), event.getPlayer().getEyeLocation()));
                }
                catch (Exception ex)
                {
                    Logger.getLogger(Listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                break;
            }
        }
    }
    
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
        for (Ability ability : ItemPlus.getAbilityManager().getAbilityList())
        {
            if (ability instanceof FireBall)
            {
                if (event.getDamager() instanceof Fireball)
                {
                    event.setDamage(((FireBall) ability).getDamage());
                    ItemPlus.getAbilityManager().getAbilityList().remove(ability);
                    return;
                }
            }
        }        
    }
}

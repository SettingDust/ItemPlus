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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * 监听器
 * <p>
 * @author HotFlow
 */
public class Listeners implements Listener
{
    @EventHandler
    @SuppressWarnings("unchecked")
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        List<Action> actions = new ArrayList<Action>();
        actions.add(event.getAction());

        @SuppressWarnings("unchecked")
        FireBall ball = new FireBall(20, 5, actions, 10L, 10);

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
        for (Ability ability : ItemPlus.getAbilityManager().getAbilityMap().values())
        {
            if (ability instanceof FireBall)
            {
                if (event.getDamager() instanceof Fireball)
                {
                    if (event.getDamager().getUniqueId().equals(((FireBall) ability).getFireball().getUniqueId()))
                    {
                        if (event.getCause().equals(DamageCause.PROJECTILE))
                        {
                            event.setDamage(((FireBall) ability).getDamage());
                            ability.delete();
                        }
                        else if (event.getCause().equals(DamageCause.ENTITY_EXPLOSION))
                        {
                            event.setDamage(((FireBall) ability).getExplodeDamage());
                        }

                        Player player = (Player) ((FireBall) ability).getFireball().getShooter();
                        player.sendMessage("火球术对 " + (event.getEntity() instanceof Player ? ((Player) event.getEntity()).getName() : event.getEntityType().getName()) + " 造成 " + event.getDamage() + " 点 " + event.getCause().name() + " 伤害。");

                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event)
    {
        for (Ability ability : ItemPlus.getAbilityManager().getAbilityMap().values())
        {
            if (ability instanceof FireBall)
            {
                if (event.getEntity() instanceof Fireball)
                {
                    if (event.getEntity().getUniqueId().equals(((FireBall) ability).getFireball().getUniqueId()))
                    {
                        ability.delete();
                    }
                }
            }
        }
    }
}

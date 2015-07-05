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

import com.ItemPlus.Event.Item.Ability.AbilityDamageEntityEvent;
import com.ItemPlus.Event.Item.Ability.AbilityEffectEvent;
import com.ItemPlus.Item.Ability.Ability;
import com.ItemPlus.Item.Ability.AbilityInfo;
import com.ItemPlus.Item.Ability.AbilityType;
import com.ItemPlus.Item.ItemStack;
import com.ItemPlus.ItemPlus;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * 技能处理
 * <p>
 * @author HotFlow
 */
public final class AbilityHandler implements Listener
{
    @EventHandler
    public void onAbility_Spell(PlayerInteractEvent event)
    {
        List<Ability> abilities = new ItemStack(event.getItem()).getAbilityList();

        try
        {
            //abilities.add(new FireBall(20, 5, new AbilityInfo(AbilityType.Point, event.getPlayer(), event.getPlayer().getEyeLocation(), 10L, 10)));
            abilities.add(new Shock(10, new AbilityInfo(AbilityType.Point, event.getPlayer(), event.getPlayer().getTargetBlock(null, 256).getLocation(), 10L, 10)));
        }
        catch (Exception ex)
        {
            Logger.getLogger(AbilityHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Ability ability : abilities)
        {
            ability.onSpell();
        }
    }

    @EventHandler
    public void onFireBall_Effect(EntityDamageByEntityEvent event)
    {
        for (Ability ability : ItemPlus.getAbilityManager().getAbilityMap().values())
        {
            if (ability instanceof FireBall)
            {
                if (event.getDamager() instanceof Fireball)
                {
                    if (event.getDamager().getUniqueId().equals(((FireBall) ability).getFireball().getUniqueId()))
                    {
                        AbilityEffectEvent evenz = new AbilityEffectEvent(ability);

                        if (evenz.isCancelled())
                        {
                            event.setCancelled(true);
                            return;
                        }

                        if (event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE))
                        {
                            AbilityDamageEntityEvent evenz1 = new AbilityDamageEntityEvent(ability, event.getEntity(), ((FireBall) ability).getDamage());
                            getServer().getPluginManager().callEvent(evenz1);

                            if (evenz1.isCancelled())
                            {
                                event.setCancelled(true);
                                return;
                            }

                            event.setDamage(((FireBall) ability).getDamage());
                            ability.onEnded();
                            return;
                        }
                        else if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION))
                        {
                            AbilityDamageEntityEvent evenz1 = new AbilityDamageEntityEvent(ability, event.getEntity(), ((FireBall) ability).getExplodeDamage());
                            getServer().getPluginManager().callEvent(evenz1);

                            if (evenz1.isCancelled())
                            {
                                event.setCancelled(true);
                                return;
                            }

                            event.setDamage(((FireBall) ability).getExplodeDamage());
                            return;
                        }

                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onFireBall_Ended(EntityExplodeEvent event)
    {
        for (Ability ability : ItemPlus.getAbilityManager().getAbilityMap().values())
        {
            if (ability instanceof FireBall)
            {
                if (event.getEntity() instanceof Fireball)
                {
                    if (event.getEntity().getUniqueId().equals(((FireBall) ability).getFireball().getUniqueId()))
                    {
                        ability.onEnded();
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onShock_Effect(EntityDamageByEntityEvent event)
    {
        for (Ability ability : ItemPlus.getAbilityManager().getAbilityMap().values())
        {
            if (ability instanceof Shock)
            {
                if (event.getDamager().getUniqueId().equals(((Shock) ability).getLightningStrike().getUniqueId()))
                {
                    AbilityEffectEvent evenz = new AbilityEffectEvent(ability);

                    if (evenz.isCancelled())
                    {
                        event.setCancelled(true);
                        return;
                    }

                    if (event.getCause().equals(EntityDamageEvent.DamageCause.LIGHTNING))
                    {
                        AbilityDamageEntityEvent evenz1 = new AbilityDamageEntityEvent(ability, event.getEntity(), ((Shock) ability).getDamage());
                        getServer().getPluginManager().callEvent(evenz1);

                        if (evenz1.isCancelled())
                        {
                            event.setCancelled(true);
                            return;
                        }

                        event.setDamage(((Shock) ability).getDamage());
                        ability.onEnded();
                        return;
                    }
                }
            }
        }
    }
}

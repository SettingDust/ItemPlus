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

import com.ItemPlus.Core.v1_0_0.Ability.FireBall;
import com.ItemPlus.Core.v1_0_0.Ability.Shock;
import com.ItemPlus.Item.ItemStack;
import com.ItemPlus.NBT.TAG;
import com.ItemPlus.NBT.TAG_Compound;
import com.ItemPlus.NBT.TAG_Double;
import com.ItemPlus.NBT.TAG_Int;
import com.ItemPlus.NBT.TAG_List;
import com.ItemPlus.NBT.TAG_Long;
import com.ItemPlus.NBT.TAG_String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 技能储存器
 * <p>
 * @author HotFlow
 */
public class AbilityStorage
{
    private final ItemStack item;
    private final List<Ability> abilities = new ArrayList<Ability>();

    /**
     * 构造技能储存器
     * <p>
     * @param item 物品
     */
    public AbilityStorage(ItemStack item)
    {
        this.item = item;
    }

    /**
     * 保存技能
     */
    public void save()
    {
        if (item.getTag() == null)
        {
            item.setTag(new TAG_Compound("tag", new HashMap<String, TAG>()));
        }

        if (item.getTag() instanceof TAG_Compound)
        {
            TAG_Compound tag = (TAG_Compound) item.getTag();

            tag.getValue().put("AbilityModifiers", new TAG_List("AbilityModifiers", TAG_Compound.class, new ArrayList<TAG>()));

            List<TAG> abilities = ((TAG_List) tag.getValue().get("AbilityModifiers")).getValue();

            Abilities:
            for (Ability ability : this.getAbilities())
            {
                Map<String, TAG> map = new HashMap<String, TAG>();
                map.put("AbilityName", new TAG_String("AbilityName", ability.getClass().getSimpleName()));
                map.put("AbilityType", new TAG_String("AbilityType", ability.getAbilityInfo().getAbilityType().name()));
                map.put("CoolDown", new TAG_Long("CoolDown", ability.getAbilityInfo().getCooldown()));
                map.put("DurabilityCast", new TAG_Int("DurabilityCast", ability.getAbilityInfo().getDurabilityCast()));
                map.put("Time", new TAG_Long("Time", ability.getTime()));

                if (ability instanceof FireBall)
                {
                    map.put("Damage", new TAG_Double("Damage", ((FireBall) ability).getDamage()));
                    map.put("ExplodeDamage", new TAG_Double("ExplodeDamage", ((FireBall) ability).getExplodeDamage()));
                }
                else if (ability instanceof Shock)
                {
                    map.put("Damage", new TAG_Double("Damage", ((Shock) ability).getDamage()));
                }

                abilities.add(new TAG_Compound("", map));
            }

            this.item.setTag(tag);
        }
    }

    /**
     * 载入技能
     */
    public void load()
    {
        if (item.getTag() == null)
        {
            item.setTag(new TAG_Compound("tag", new HashMap<String, TAG>()));
        }

        if (item.getTag() instanceof TAG_Compound)
        {
            TAG_Compound tag = (TAG_Compound) item.getTag();

            if (!tag.getValue().containsKey("AbilityModifiers"))
            {
                tag.getValue().put("AbilityModifiers", new TAG_List("AbilityModifiers", TAG_Compound.class, new ArrayList<TAG>()));
            }

            List<TAG> abilities = ((TAG_List) tag.getValue().get("AbilityModifiers")).getValue();

            for (int i = 0; i < abilities.size(); i++)
            {
                if (abilities.get(i) instanceof TAG_Compound)
                {
                    TAG_Compound ability = (TAG_Compound) abilities.get(i);

                    final String abilityName = ((TAG_String) ability.getValue().get("AbilityName")).getValue();
                    String abilityType = ((TAG_String) ability.getValue().get("AbilityType")).getValue();
                    Long cooldown = ((TAG_Long) ability.getValue().get("CoolDown")).getValue();
                    Integer durabilityCast = ((TAG_Int) ability.getValue().get("DurabilityCast")).getValue();
                    Long time = ((TAG_Long) ability.getValue().get("Time")).getValue();

                    if (abilityName.equalsIgnoreCase("FireBall"))
                    {
                        Double damage = ((TAG_Double) ability.getValue().get("Damage")).getValue();
                        Double explodeDamage = ((TAG_Double) ability.getValue().get("ExplodeDamage")).getValue();
                        FireBall ball = new FireBall(damage, explodeDamage, new AbilityInfo(AbilityType.valueOf(abilityType), null, null, null, cooldown, durabilityCast));
                        ball.setTime(time);
                        this.abilities.add(ball);
                    }
                    else if (abilityName.equalsIgnoreCase("Shock"))
                    {
                        Double damage = ((TAG_Double) ability.getValue().get("Damage")).getValue();
                        Shock shock = new Shock(damage, new AbilityInfo(AbilityType.valueOf(abilityType), null, null, null, cooldown, durabilityCast));
                        shock.setTime(time);
                        this.abilities.add(shock);
                    }
                }
            }
        }
    }

    /**
     * 获取技能列表
     * <p>
     * @return List<Ability>
     */
    public List<Ability> getAbilities()
    {
        return this.abilities;
    }
}

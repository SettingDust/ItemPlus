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

package com.ItemPlus.Core.v0_0_1.Command;

import com.ItemPlus.CommandExecutor.ItemCommand;
import com.ItemPlus.CommandExecutor.ItemExecutor;
import com.ItemPlus.Core.v0_0_1.Utils.ISystem;
import com.ItemPlus.Item.Attribute.Attribute;
import com.ItemPlus.Item.Attribute.AttributeStorage;
import com.ItemPlus.Item.ItemStack;
import com.ItemPlus.NBT.TAG;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 测试指令
 * <p>
 * @author HotFlow
 */
public class TestCommands implements ItemExecutor
{

    @ItemCommand(value = "nbt", comments = "nbt测试指令")
    public Boolean execute(CommandSender sender, final String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;

            if (player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR)
            {
                try
                {
                    TAG tag = new ItemStack(player.getItemInHand()).getTag();

                    if (tag != null)
                    {
                        player.sendMessage(tag.toString());
                        return true;
                    }
                    else
                    {
                        player.sendMessage("物品没有Tag!");
                        return false;
                    }
                }
                catch (Exception ex)
                {
                    Logger.getLogger(TestCommands.class.getName()).log(Level.SEVERE, null, ex);
                }

                player.sendMessage("物品Tag格式不正确!");
                return false;
            }
        }

        return false;
    }

    @ItemCommand(value = "attributes", comments = "属性操作测试指令。")
    public Boolean attributesExecute(CommandSender sender, final String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            if (args.length > 0)
            {
                if (args[0].equalsIgnoreCase("add"))
                {
                    if (args.length > 3)
                    {
                        if (player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR)
                        {
                            if (ISystem.isInt(args[3]))
                            {
                                Attribute attribute = new Attribute(args[1], 0, Integer.parseInt(args[3]), UUID.randomUUID())
                                {
                                    @Override
                                    public String getTypeString()
                                    {
                                        return args[2];
                                    }
                                };

                                AttributeStorage storage = new AttributeStorage(new ItemStack(player.getItemInHand()));
                                storage.getAttributes().add(attribute);
                                player.setItemInHand(storage.save());
                                player.sendMessage("成功添加属性！");
                                return true;
                            }
                            else
                            {
                                player.sendMessage("[值]必须为整数!");
                                player.sendMessage("/ItemPlus attributes add [名字] [属性] [值]");
                                return false;
                            }
                        }
                        else
                        {
                            player.sendMessage("你的手上并没有任何东西!");
                            return false;
                        }
                    }
                    else
                    {
                        player.sendMessage("/ItemPlus attributes add [名字] [属性] [值]");
                        return false;
                    }
                }
                else
                {
                    player.sendMessage("/ItemPlus attribute <add|remove>");
                    return false;
                }
            }
            else
            {
                if (player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR)
                {
                    player.sendMessage("属性列表:");
                    AttributeStorage storage = new AttributeStorage(new ItemStack(player.getItemInHand()));

                    for (Attribute attribute : storage.getAttributes())
                    {
                        player.sendMessage(" - " + attribute.getName());
                    }
                    return true;
                }
                else
                {
                    player.sendMessage("你的手上并没有任何东西!");
                    return false;
                }
            }
        }
        return true;
    }
}

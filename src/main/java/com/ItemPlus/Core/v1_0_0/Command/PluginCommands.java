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

package com.ItemPlus.Core.v1_0_0.Command;

import com.ItemPlus.CommandExecutor.ItemCommand;
import com.ItemPlus.CommandExecutor.ItemExecutor;
import com.ItemPlus.Core.v1_0_0.Utils.ISystem;
import com.ItemPlus.Item.Attribute.Attribute;
import com.ItemPlus.Item.Attribute.AttributeStorage;
import com.ItemPlus.Item.ItemStack;
import com.ItemPlus.ItemPlus;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 插件指令
 * <p>
 * @author HotFlow
 */
public class PluginCommands implements ItemExecutor
{
    @ItemCommand(value = "help", comments = "查看所有指令。")
    public Boolean execute(CommandSender sender, final String[] args)
    {
        Map<String, String> cs = new HashMap<String, String>();
        for (ItemExecutor executor : ItemPlus.getCommandManager().getCommandExecutors())
        {
            if (executor != null)
            {
                Method[] methods = executor.getClass().getMethods();
                for (Method method : methods)
                {
                    if ((method.getReturnType() == Boolean.class))
                    {
                        if (method.getParameterTypes().length >= 2)
                        {
                            if ((method.getParameterTypes()[0] == CommandSender.class) && (method.getParameterTypes()[1] == String[].class))
                            {
                                Annotation[] annotations = method.getAnnotations();
                                for (Annotation annotation : annotations)
                                {
                                    if (annotation.annotationType() == ItemCommand.class)
                                    {
                                        ItemCommand itemCommand = (ItemCommand) annotation;

                                        Boolean existed = false;
                                        for (String c : cs.keySet())
                                        {
                                            if (c.equalsIgnoreCase(itemCommand.value()))
                                            {
                                                existed = true;
                                            }
                                        }

                                        if (!existed)
                                        {
                                            cs.put(itemCommand.value(), itemCommand.comments());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (args.length == 0 || !ISystem.isInt(args[0]))
        {
            int page = (cs.keySet().size() / 10);
            sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "ItemPlus指令页: 1/" + (page < 1 ? 1 : ((cs.keySet().size() % 10) > 0 ? page + 1 : page)));
            int i = 0;
            for (String c : cs.keySet())
            {
                if (++i <= 10)
                {
                    sender.sendMessage(ChatColor.GOLD + "/ItemPlus " + c + ": " + ChatColor.WHITE + cs.get(c));
                }
            }
            sender.sendMessage(ChatColor.RED + "请输入" + ChatColor.GOLD + "/ItemPlus help [页数] " + ChatColor.RED + "翻页。");
        }
        else
        {
            int page = (cs.keySet().size() / 10);

            if (Integer.parseInt(args[0]) < 1)
            {
                args[0] = "1";
            }
            else if (Integer.parseInt(args[0]) > page)
            {
                args[0] = String.valueOf((page < 1 ? 1 : ((cs.keySet().size() % 10) > 0 ? page + 1 : page)));
            }

            sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "ItemPlus指令页: " + args[0] + "/" + (page < 1 ? 1 : ((cs.keySet().size() % 10) > 0 ? page + 1 : page)));
            int i = 0;

            for (String c : cs.keySet())
            {
                i++;
                if (i >= ((Integer.parseInt(args[0]) - 1) * 10) && i <= ((Integer.parseInt(args[0]) - 1) * 10) + 9)
                {
                    sender.sendMessage(ChatColor.GOLD + "/ItemPlus " + c + ": " + ChatColor.WHITE + cs.get(c));
                }
            }

            sender.sendMessage(ChatColor.RED + "请输入" + ChatColor.GOLD + "/ItemPlus help [页数] " + ChatColor.RED + "翻页。");
        }

        return true;
    }

    @ItemCommand(value = "attribute", comments = "属性功能指令。")
    public Boolean attributeCommand(CommandSender sender, final String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;

            if (player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR)
            {
                AttributeStorage storage = new AttributeStorage(new ItemStack(player.getItemInHand()));

                if (args.length > 0)
                {
                    if (args[0].equalsIgnoreCase("check"))
                    {
                        player.sendMessage("属性列表:");

                        for (Attribute attribute : storage.getAttributes())
                        {
                            player.sendMessage(" - " + attribute.getName() + "(" + attribute.getTypeString() + ")");
                        }

                        return true;
                    }
                    else if (args[0].equalsIgnoreCase("add"))
                    {
                        if (args.length > 4)
                        {
                            if (ISystem.isNum(args[3]) && ISystem.isInt(args[4]))
                            {
                                Attribute attribute = new Attribute(args[1], Integer.parseInt(args[4]), Double.parseDouble(args[3]), UUID.randomUUID())
                                {
                                    @Override
                                    public String getTypeString()
                                    {
                                        return args[2];
                                    }
                                };

                                storage.getAttributes().add(attribute);
                                storage.save();
                                player.sendMessage("成功添加属性!");
                                return true;
                            }
                            else
                            {
                                player.sendMessage("请按照格式发出命令!");
                            }
                        }

                        player.sendMessage("/ItemPlus attribute add [名字] [属性] [值] [操作方式]");
                        return false;
                    }
                    else if (args[0].equalsIgnoreCase("remove"))
                    {
                        if (args.length > 1)
                        {
                            for (Attribute attribute : storage.getAttributes())
                            {
                                if (attribute.getName().equalsIgnoreCase(args[1]) || attribute.getName().replace("\"", "").equalsIgnoreCase(args[1]))
                                {
                                    storage.getAttributes().remove(attribute);
                                    storage.save();
                                    player.sendMessage("成功移除属性!");
                                    return true;
                                }
                            }

                            player.sendMessage("属性不存在!");
                            return false;
                        }

                        player.sendMessage("/ItemPlus attribute remove [名字]");
                        return false;
                    }
                    else
                    {
                        player.sendMessage("命令不存在!");
                    }
                }

                player.sendMessage(ChatColor.GOLD + "/ItemPlus attribute check" + ": " + ChatColor.WHITE + "查看当前物品属性。");
                player.sendMessage(ChatColor.GOLD + "/ItemPlus attribute add" + ": " + ChatColor.WHITE + "为当前物品添加属性。");
                player.sendMessage(ChatColor.GOLD + "/ItemPlus attribute remove" + ": " + ChatColor.WHITE + "为当前物品移除属性。");
                return false;
            }
            else
            {
                player.sendMessage("你的手上并没有任何东西!");
                return false;
            }
        }
        else
        {
            sender.sendMessage("该命令只能由玩家发出!");
            return false;
        }
    }
}

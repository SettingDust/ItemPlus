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
import com.ItemPlus.ItemPlus;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

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
}

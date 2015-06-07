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

package com.ItemPlus.CommandExecutor;

import com.ItemPlus.Event.Plugin.PluginExecuteCommandEvent;
import com.ItemPlus.ItemPlus;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * ItemPlus的命令执行器处理器
 * <p>
 * @author HotFlow
 */
public final class ItemExecutorHandler implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, final String[] args)
    {
        if (args.length > 0)
        {
            Boolean returnResult = false;
            Map<String, String> cs = new HashMap<String, String>();
            Boolean executed = false;

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

                                            if (args.length > 0)
                                            {
                                                if (itemCommand.value().equalsIgnoreCase(args[0]))
                                                {
                                                    try
                                                    {
                                                        executed = true;

                                                        String[] newArgs = new String[args.length - 1];

                                                        for (int i = 0; i < newArgs.length; i++)
                                                        {
                                                            newArgs[i] = args[i + 1];
                                                        }

                                                        PluginExecuteCommandEvent event = new PluginExecuteCommandEvent(ItemPlus.getPlugin(), sender, itemCommand.value(), newArgs);

                                                        if (event.isCancelled())
                                                        {
                                                            continue;
                                                        }

                                                        returnResult = (Boolean) method.invoke(executor, event.getCommandSender(), event.getArgs());
                                                    }
                                                    catch (IllegalAccessException ex)
                                                    {
                                                        Logger.getLogger(ItemExecutorHandler.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                    catch (IllegalArgumentException ex)
                                                    {
                                                        Logger.getLogger(ItemExecutorHandler.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                    catch (InvocationTargetException ex)
                                                    {
                                                        Logger.getLogger(ItemExecutorHandler.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }

            if (!executed)
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

            return returnResult;
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "请输入 " + ChatColor.GOLD + "/ItemPlus help " + ChatColor.RED + "查看插件指令!");
            return false;
        }
    }
}

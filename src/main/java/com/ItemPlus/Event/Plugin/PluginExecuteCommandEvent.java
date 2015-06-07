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

package com.ItemPlus.Event.Plugin;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.plugin.Plugin;

/**
 * 插件执行命令事件
 * <p>
 * @author HotFlow
 */
public class PluginExecuteCommandEvent extends PluginEvent implements Cancellable
{
    private Boolean cancelled = false;
    private final String command;
    private final CommandSender sender;
    private String[] args;

    /**
     * 构造插件执行命令事件
     * <p>
     * @param plugin 插件
     * @param sender 发送者
     * @param command 命令
     * @param args 参数
     */
    public PluginExecuteCommandEvent(Plugin plugin, CommandSender sender, String command, String[] args)
    {
        super(plugin);
        this.sender = sender;
        this.command = command;
        this.args = args;
    }

    /**
     * 获取发送者
     * <p>
     * @return
     */
    public CommandSender getCommandSender()
    {
        return this.sender;
    }

    /**
     * 获取命令
     * <p>
     * @return String
     */
    public String getCommand()
    {
        return this.command;
    }

    /**
     * 获取参数
     * <p>
     * @return String[]
     */
    public String[] getArgs()
    {
        return this.args;
    }

    /**
     * 设置参数
     * <p>
     * @param args 参数
     */
    public void setArgs(String[] args)
    {
        this.args = args;
    }

    @Override
    public boolean isCancelled()
    {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean bln)
    {
        this.cancelled = bln;
    }
}

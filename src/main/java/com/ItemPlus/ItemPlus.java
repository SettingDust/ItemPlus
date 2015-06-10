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

package com.ItemPlus;

import com.ItemPlus.Core.v0_0_1.LoggerHandler;
import com.ItemPlus.Core.v0_0_1.Manager.CommandManager;
import com.ItemPlus.Core.v0_0_1.Manager.LoggerManager;
import com.ItemPlus.Core.v0_0_1.Manager.PlayerManager;
import com.ItemPlus.Core.v0_0_1.Manager.TaskManager;
import com.ItemPlus.Event.Plugin.PluginDisableEvent;
import com.ItemPlus.Event.Plugin.PluginEnableEvent;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author HotFlow
 */
public class ItemPlus extends JavaPlugin
{
    private static ItemPlus plugin;
    private final static String prefix = "[ItemPlus] ";
    public static final LoggerManager logger = new LoggerManager();
    private static final CommandManager commandManager = new CommandManager();
    private static final PlayerManager playerManager = new PlayerManager();
    private static final TaskManager taskManager = new TaskManager();
    private static Economy economyManager;
    private static Permission permissionManager;
    private static Chat chatManager;

    @Override
    public void onEnable()
    {
        ItemPlus.setPlugin(this);

        PluginEnableEvent event = new PluginEnableEvent(this);
        getServer().getPluginManager().callEvent(event);

        if (event.isCancelled())
        {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        ItemPlus.logger.getLogger().addHandler(new LoggerHandler()
        {
            @Override
            public void publish(final LogRecord record)
            {
                record.setMessage(ItemPlus.getPrefix() + record.getMessage());
            }

        });

        getServer().getPluginManager().registerEvents(new com.ItemPlus.Core.v0_0_1.Listener.Listeners(), this);
        getCommand("ItemPlus").setExecutor(new com.ItemPlus.CommandExecutor.ItemExecutorHandler());
        ItemPlus.getCommandManager().getCommandExecutors().add(new com.ItemPlus.Core.v0_0_1.Command.PluginCommands());
        ItemPlus.getCommandManager().getCommandExecutors().add(new com.ItemPlus.Core.v0_0_1.Command.TestCommands());
    }

    @Override
    public void onDisable()
    {
        PluginDisableEvent event = new PluginDisableEvent(this);
        getServer().getPluginManager().callEvent(event);
        for (Handler handler : ItemPlus.logger.getLogger().getHandlers())
        {
            ItemPlus.logger.getLogger().removeHandler(handler);
        }
    }

    /**
     * 获取前缀
     * <p>
     * @return String
     */
    public static String getPrefix()
    {
        return ItemPlus.prefix;
    }

    /**
     * 获取插件
     * <p>
     * @return ItemPlus
     */
    public static ItemPlus getPlugin()
    {
        return ItemPlus.plugin;
    }

    /**
     * 设置插件
     * <p>
     * @param plugin 插件
     */
    private static void setPlugin(ItemPlus plugin)
    {
        ItemPlus.plugin = plugin;
    }

    /**
     * 获取玩家管理器
     * <p>
     * @return PlayerManager
     */
    public static PlayerManager getPlayerManager()
    {
        return ItemPlus.playerManager;
    }

    /**
     * 获取命令管理器
     * <p>
     * @return CommandManager
     */
    public static CommandManager getCommandManager()
    {
        return ItemPlus.commandManager;
    }

    /**
     * 获取执行器管理器
     * <p>
     * @return TaskManager
     */
    public static TaskManager getTaskManager()
    {
        return ItemPlus.taskManager;
    }

    /**
     * 安装经济系统
     * <p>
     * @return Boolean
     */
    public static Boolean setupEconomy()
    {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null)
        {
            return false;
        }

        RegisteredServiceProvider economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

        if (economyProvider != null)
        {
            ItemPlus.economyManager = (Economy) economyProvider.getProvider();
        }

        return ItemPlus.economyManager != null;
    }

    /**
     * 安装权限系统
     * <p>
     * @return Boolean
     */
    public static Boolean setupPermission()
    {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null)
        {
            return false;

        }

        RegisteredServiceProvider permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);

        if (permissionProvider != null)
        {
            ItemPlus.permissionManager = (Permission) permissionProvider.getProvider();
        }

        return ItemPlus.permissionManager != null;
    }

    /**
     * 安装频道系统
     * <p>
     * @return Boolean
     */
    public static Boolean setupChat()
    {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null)
        {
            return false;

        }

        RegisteredServiceProvider chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);

        if (chatProvider != null)
        {
            ItemPlus.chatManager = (Chat) chatProvider.getProvider();
        }

        return ItemPlus.chatManager != null;
    }
}

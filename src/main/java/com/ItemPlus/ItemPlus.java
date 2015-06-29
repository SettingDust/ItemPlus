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

import com.ItemPlus.Core.v1_0_0.Attribute.AttackDamage;
import com.ItemPlus.Core.v1_0_0.Attribute.KnockbackResistance;
import com.ItemPlus.Core.v1_0_0.Attribute.MaxHealth;
import com.ItemPlus.Core.v1_0_0.Attribute.MovementSpeed;
import com.ItemPlus.Core.v1_0_0.LoggerHandler;
import com.ItemPlus.Core.v1_0_0.Manager.AbilityManager;
import com.ItemPlus.Core.v1_0_0.Manager.AttributeManager;
import com.ItemPlus.Core.v1_0_0.Manager.BuffManager;
import com.ItemPlus.Core.v1_0_0.Manager.CommandManager;
import com.ItemPlus.Core.v1_0_0.Manager.LoggerManager;
import com.ItemPlus.Core.v1_0_0.Manager.PlayerManager;
import com.ItemPlus.Core.v1_0_0.Manager.TaskManager;
import com.ItemPlus.Core.v1_0_0.Script.ScriptHandler;
import com.ItemPlus.Event.Plugin.PluginDisableEvent;
import com.ItemPlus.Event.Plugin.PluginEnableEvent;
import com.ItemPlus.Timer.ServerTimer;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

/**
 * @author HotFlow
 */
public class ItemPlus extends JavaPlugin
{
    private static ItemPlus plugin;
    private final static String prefix = "[ItemPlus] ";
    private static ServerTimer timer;
    public static final LoggerManager logger = new LoggerManager();
    private static final CommandManager commandManager = new CommandManager();
    private static final PlayerManager playerManager = new PlayerManager();
    private static final TaskManager taskManager = new TaskManager();
    private static final AbilityManager abilityManager = new AbilityManager();
    private static final BuffManager buffManager = new BuffManager();
    private static final AttributeManager attributeManager = new AttributeManager();
    private static Economy economyManager;
    private static Permission permissionManager;
    private static Chat chatManager;

    public static void main(String[] args)
    {
        String nr = "\r\n";
        String test = "if(\"s\" == \"s\")" + nr;
        test += "{" + nr;
        test += "    print(\"测试成功!\");" + nr;
        test += "}";

        try
        {
            ScriptHandler.run(test);
        }
        catch (Exception ex)
        {
            Logger.getLogger(ItemPlus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

        ItemPlus.timer = new ServerTimer();
        ItemPlus.getServerTimer().getTimerRunnable().start();

        ItemPlus.getAttributeManager().getAttributes().put("AttackDamage", AttackDamage.class);
        ItemPlus.getAttributeManager().getAttributes().put("KnockbackResistance", KnockbackResistance.class);
        ItemPlus.getAttributeManager().getAttributes().put("MaxHealth", MaxHealth.class);
        ItemPlus.getAttributeManager().getAttributes().put("MovementSpeed", MovementSpeed.class);

        getServer().getPluginManager().registerEvents(new com.ItemPlus.Core.v1_0_0.Listener.Listeners(), this);
        getServer().getPluginManager().registerEvents(new com.ItemPlus.Core.v1_0_0.Ability.AbilityHandler(), this);
        getServer().getPluginManager().registerEvents(new com.ItemPlus.Core.v1_0_0.Ability.Buffs.BuffHandler(), this);

        getCommand("ItemPlus").setExecutor(new com.ItemPlus.CommandExecutor.ItemExecutorHandler());
        ItemPlus.getCommandManager().getCommandExecutors().add(new com.ItemPlus.Core.v1_0_0.Command.PluginCommands());
        ItemPlus.getCommandManager().getCommandExecutors().add(new com.ItemPlus.Core.v1_0_0.Command.TestCommands());

        try
        {
            Metrics metrics = new Metrics(this);
            metrics.start();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ItemPlus.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * 获取插件时间
     * <p>
     * @return ServerTimer
     */
    public static ServerTimer getServerTimer()
    {
        return ItemPlus.timer;
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
     * 获取技能管理器
     * <p>
     * @return AbilityManager
     */
    public static AbilityManager getAbilityManager()
    {
        return ItemPlus.abilityManager;
    }

    /**
     * 获取状态管理器
     * <p>
     * @return BuffManager
     */
    public static BuffManager getBuffManager()
    {
        return ItemPlus.buffManager;
    }

    /**
     * 获取属性管理器
     * <p>
     * @return AttributeManager
     */
    public static AttributeManager getAttributeManager()
    {
        return ItemPlus.attributeManager;
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

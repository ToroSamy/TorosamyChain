package net.torosamy.torosamyChain;

import net.torosamy.torosamyCore.utils.MessageUtil;
import net.torosamy.torosamyChain.utils.CommandUtil;
import net.torosamy.torosamyChain.utils.ConfigUtil;
import net.torosamy.torosamyChain.utils.ListenerUtil;
import net.torosamy.torosamyChain.utils.SchedulerUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class TorosamyChain extends JavaPlugin {
    public static TorosamyChain instance;
    
    @Override
    public void onEnable() {
        instance = this;
        ConfigUtil.initConfig();
        ConfigUtil.reloadConfig();
        CommandUtil.registerCommand();
        SchedulerUtil.registerScheduler();
        ListenerUtil.registerListener();

        Bukkit.getConsoleSender().sendMessage(MessageUtil.format("&b[服务器娘]&a插件 &eTorosamyChain &a成功开启喵~"));
 
    }

    @Override
    public void onDisable() {
        ConfigUtil.saveConfig();

        Bukkit.getConsoleSender().sendMessage(MessageUtil.format("&b[服务器娘]&c插件 &eTorosamyChain &c成功关闭喵~"));
        Bukkit.getConsoleSender().sendMessage(MessageUtil.format("&b[服务器娘]&c作者 &eTorosamy|yweiyang"));
    }
}

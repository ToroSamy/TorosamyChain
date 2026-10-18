package net.torosamy.torosamyChain.utils;


import net.torosamy.torosamyChain.TorosamyChain;
import net.torosamy.torosamyChain.listener.BlockBreakListener;
import net.torosamy.torosamyChain.listener.BlockInteractListener;
import net.torosamy.torosamyChain.listener.UpdateListener;

public class ListenerUtil {
    public static void registerListener() {
        TorosamyChain.instance.getServer().getPluginManager().registerEvents(new BlockBreakListener(), TorosamyChain.instance);
        TorosamyChain.instance.getServer().getPluginManager().registerEvents(new UpdateListener(), TorosamyChain.instance);
        TorosamyChain.instance.getServer().getPluginManager().registerEvents(new BlockInteractListener(), TorosamyChain.instance);
     }
}

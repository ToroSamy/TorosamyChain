package net.torosamy.torosamyChain.listener;


import net.torosamy.torosamyChain.api.TorosamyChainAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class UpdateListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        TorosamyChainAPI.addToggle(player.getName());
    }
    
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        TorosamyChainAPI.removeToggle(e.getPlayer().getName());
    }
}

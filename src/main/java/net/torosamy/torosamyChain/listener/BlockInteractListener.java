package net.torosamy.torosamyChain.listener;

import net.torosamy.torosamyChain.chain.CropPlantChain;
import net.torosamy.torosamyChain.utils.ConfigUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockInteractListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void interact(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null ) {
            return;
        }

        Player player = event.getPlayer();

        String type = clickedBlock.getType().toString();
        
        String permission = ConfigUtil.mainConfig.plantSeedPermissionPrefix + ".";
        if (!player.hasPermission(permission + "*") &&
            !player.hasPermission(permission + type.toLowerCase())) {
            return;
        }

        CropPlantChain.chains(player, clickedBlock);
    }
}

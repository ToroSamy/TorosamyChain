package net.torosamy.torosamyChain.listener;

import net.torosamy.torosamyChain.chain.CropBreakChain;
import net.torosamy.torosamyChain.chain.OreBreakChain;
import net.torosamy.torosamyChain.chain.TreeBreakChain;
import net.torosamy.torosamyChain.utils.ConfigUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Deque;
import java.util.HashSet;
import java.util.List;


public class BlockBreakListener implements Listener {
    private static final HashSet<Block> blocksIgnored = new HashSet<>();
    
    public static void ignore(Block block) {
        blocksIgnored.add(block);
    }
    
    public static void ignore(List<Block> blocks) {
        blocksIgnored.addAll(blocks);
    }
    
    public static void unIgnore(Deque<Block> blocks) {
        blocks.forEach(blocksIgnored::remove);
    }
    
    public static void unIgnore(Block block) {
        blocksIgnored.remove(block);
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player player = e.getPlayer();
        
        if (blocksIgnored.contains(e.getBlock())) {
            blocksIgnored.remove(e.getBlock());
            return;
        }


        
        String type = e.getBlock().getType().toString();

        String tool = player.getInventory().getItemInMainHand().getType().toString();
        
        String blockPermission = ConfigUtil.mainConfig.blockBreakPermissionPrefix + ".";
        if (!player.hasPermission(blockPermission + "*") &&
            !player.hasPermission(blockPermission + type.toLowerCase())) {
            return;
        }

        String toolPermission = ConfigUtil.mainConfig.useToolPermissionPrefix + ".";
        if (!player.hasPermission(toolPermission + "*") &&
            !player.hasPermission(toolPermission + tool.toLowerCase())) {
            return;
        }
     
        if (tool.contains("_HOE")) {
            CropBreakChain.chains(player, e.getBlock());
            return;
        }

        if (tool.contains("_AXE")) {
            TreeBreakChain.chains(player, e.getBlock());
            return;
        }
        
        
        if (tool.contains("_PICKAXE")) {
            OreBreakChain.chains(player, e.getBlock());
            return;
        }
        
    }
}
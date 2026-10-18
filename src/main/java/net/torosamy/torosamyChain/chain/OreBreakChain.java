package net.torosamy.torosamyChain.chain;

import java.util.List;

import net.torosamy.torosamyChain.api.TorosamyChainAPI;
import net.torosamy.torosamyChain.api.BaseChain;
import net.torosamy.torosamyChain.listener.BlockBreakListener;
import net.torosamy.torosamyChain.scheduler.ChainBlockTask;
import net.torosamy.torosamyChain.utils.ConfigUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;


public class OreBreakChain extends BaseChain {
    private final Material type;

    public static boolean chains(Player player, Block block) {
        if (!TorosamyChainAPI.canChain(player, ConfigUtil.mainConfig.mining)) {
            return false;
        }

        if (!TorosamyChainAPI.isMining(player.getName())) {
            return false;
        }
        if (!isOre(block.getType())) {
            return false;
        }

        ChainBlockTask.getInstance().poll(new OreBreakChain(player, block));
        return true;
    }
    
    
    
    private OreBreakChain(Player player, Block block) {
        super(player);
        this.type = block.getType();
        chains(block);
    }

    @Override
    public boolean compute() {
        if (player == null || !player.isOnline() ||  going.isEmpty()) {
            return cancel();
        }
        
        Block block = going.poll();
        
        if (!block.getType().equals(type)) {
            return cancel();
        }

        if (!block.getWorld().equals(player.getWorld())) {
            return cancel();
        }
        
        if (!isPickaxe(player)) {
            return cancel();
        }

        if (!player.breakBlock(block)) {
            return cancel();
        }

        chains(block);

        block.getWorld().playSound(block.getLocation(), block.getBlockData().getSoundGroup().getBreakSound(), 1.0F, 1.0F);

        return going.isEmpty();
    }

    @Override
    protected void chains(Block block) {
        BlockBreakListener.unIgnore(block);
        
        List<Block> list = TorosamyChainAPI.getNear(block).stream().filter(it-> {
            if (!it.getType().equals(this.type)) {
                return false;
            }
            if (this.going.contains(it)) {
                return false;
            }
            return true;
        }).toList();
        
        this.going.addAll(list);

        BlockBreakListener.ignore(list);
    }


    private static boolean isPickaxe(Player player) {
        return player.getInventory().getItemInMainHand().getType().toString().contains("_PICKAXE");
    }
    
    private static boolean isOre(Material material) {
        return material.toString().contains("_ORE");
    }
}
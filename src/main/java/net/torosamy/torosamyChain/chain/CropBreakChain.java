package net.torosamy.torosamyChain.chain;

import net.torosamy.torosamyChain.api.TorosamyChainAPI;
import net.torosamy.torosamyChain.api.BaseChain;
import net.torosamy.torosamyChain.listener.BlockBreakListener;
import net.torosamy.torosamyChain.scheduler.ChainBlockTask;
import net.torosamy.torosamyChain.utils.ConfigUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CropBreakChain extends BaseChain {
    
    public static boolean chains(Player player, Block block) {
        if (!TorosamyChainAPI.canChain(player, ConfigUtil.mainConfig.farming)) {
            return false;
        }

        if (!TorosamyChainAPI.isFarming(player.getName())) {
            return false;
        }
        
        if (!isCrop(block)) {
            return false;
        }

        
        ChainBlockTask.getInstance().poll(new CropBreakChain(player, block));
        return true;
    }
    
    private CropBreakChain(Player player, Block block) {
        super(player);
        this.going.add(block);
        chains(block);
    }
    
    @Override
    protected void chains(Block block) {
        BlockBreakListener.unIgnore(block);

        List<Block> blocks = TorosamyChainAPI.getNear(block).stream().map(CropBreakChain::getFarmBlock).filter(it -> {
            if (isCrop(it) && !going.contains(it) && !it.getType().equals(Material.SUGAR_CANE)) {
                return true;
            }
            if (it.getType().equals(Material.SUGAR_CANE) && it.getY() == block.getY() && !this.going.contains(it)) {
                return true;
            }
            return false;
        }).toList();
        
        going.addAll(blocks);
        BlockBreakListener.ignore(blocks);
    }
    
    
    @Override
    public boolean compute() {
        if (!player.isOnline() || going.isEmpty()) {
           return true;
        }
        Block block = going.poll();
   
        if (!canHarvest(block)) {
            return going.isEmpty();
        }

        
        block.getWorld().playSound(block.getLocation(), block.getBlockData().getSoundGroup().getBreakSound(), 1.0F, 1.0F);

        if (block.getWorld().equals(player.getWorld()) && player.breakBlock(block) && isHoe(player)) {
            chains(block);
            return going.isEmpty();
        } 
        
        return cancel();
    }
 


    private static Block getFarmBlock(Block b) {
        if (b.getType().equals(Material.FARMLAND) || b.getType().equals(Material.SOUL_SAND)) {
            return b.getRelative(BlockFace.UP);
        }
        return b;
    }
    
    private static boolean isCrop(Block block) {
        return block.getBlockData() instanceof Ageable;
    }
    
    private static boolean canHarvest(Block block) {
        if (!isCrop(block)) {
            return false;
        }
        
        Ageable ageable = (Ageable) block.getBlockData();

        if (ageable.getAge() == ageable.getMaximumAge()) {
            return true;
        }
        return block.getType().equals(Material.SUGAR_CANE);
    }
   
    private static boolean isHoe(Player player) {
        if (player == null) {
            return false;
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        
        if (item.getType() == Material.AIR) {
            return false;
        }
        
        return item.getType().toString().contains("_HOE");
    }
}
